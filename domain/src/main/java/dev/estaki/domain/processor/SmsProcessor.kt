package dev.estaki.domain.processor

import dev.estaki.domain.models.SmsModel
import dev.estaki.domain.models.SmsRawModel
import dev.estaki.domain.models.TransactionType
import dev.estaki.kt_pure_utils.isProbablyArabicOrPersian
import dev.estaki.kt_pure_utils.removeFarsiChar
import dev.estaki.kt_pure_utils.removeSpecialChar
import kotlin.text.split


class SmsProcessor(private var smsRawModelList: MutableList<SmsRawModel>) {
    fun execute(): List<SmsModel>? {
        return assertAndQualify()
    }

    private fun assertAndQualify(): List<SmsModel>? {
        assertOfSender()
        assertOfDescription()
        return parsSms()
    }

    private fun assertOfSender() {
        try {
            smsRawModelList.removeAll { sms ->
                sms.senderName.startsWith("+989") ||
                        sms.senderName.startsWith("98") ||
                        sms.senderName.startsWith("+98") ||
                        sms.senderName.contains("HAMRAHAVAL", true) ||
                        sms.senderName.contains("IRANCELL", true) ||
                        sms.senderName.contains("RIGHTEL", true) ||
                        sms.senderName.contains("DIGIPAY", true)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun assertOfDescription() {
        try {
            val smsList = smsRawModelList.filter { sms ->
                (
                        sms.description.contains("واریز") ||
                                sms.description.contains("واريز") ||
                                sms.description.contains("واریز به") ||
                                sms.description.contains("واریز حقوق") ||
                                sms.description.contains("برداشت") ||
                                sms.description.contains("برداشت از") ||
                                sms.description.contains("+") ||
                                sms.description.contains("-") ||
                                sms.description.contains("حساب")
                        )
                        &&
                        (sms.description.contains("مانده") || sms.description.contains("موجودی") || sms.description.contains(
                            "موجودي"
                        ))
            }
            val finalResult = smsList.toMutableList()
            finalResult.removeAll { s ->
                (s.description.contains("مشتری") ||
                        s.description.contains("مشتر") ||
                        s.description.contains("مشترک") ||
                        s.description.contains("گرامی") ||
                        s.description.contains("محترم") ||
                        s.description.contains("جناب") ||
                        s.description.contains("آقای") ||
                        s.description.contains("خانم") ||
                        s.description.contains("قرعه") ||
                        s.description.contains("طلایی")
                        )
            }
            smsRawModelList = finalResult
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun parsSms(): List<SmsModel>? {
        val listOfModel = mutableListOf<SmsModel>()
        try {
            smsRawModelList.forEach { sms ->
                val dateRegex =
                    Regex("\\d{2,4}+\\/\\d{1,2}\\/\\d{2,4}|([0-1]?[0-9]|2[0-3])\\/[0-5][0-9]")
                val dateRegexMatch = dateRegex.find(sms.description)
                val date = dateRegexMatch?.groups?.first()?.value

                val timeRegex = Regex("([0-9]{2}+)(:[0-9]{2}+)(:[0-9]{2})*")
                val timeRegexMatch = timeRegex.find(sms.description)
                val time = timeRegexMatch?.groups?.first()?.value

                val split = sms.description.split("\n")
                var finalBankAccountNumber: String =
                    parsBankAccountNumber(sms.senderName, sms.description)


                var amount = ""
                var transactionType: TransactionType = if (!split.find {
                        it.contains("برداشت") || it.contains("-")
                    }.isNullOrBlank()) TransactionType.WITHDRAW else TransactionType.DEPOSIT

                Regex("""(?:مبلغ:|برداشت:|واریز:|واریز حقوق:)\s?(\d{1,3}(?:,\d{3})+)|(\d{1,3}(?:,\d{3})+)(?:\+|-)""")
                    .findAll(sms.description).forEach {
                        amount = it.groups.first()?.value.toString()
                        transactionType = if (it.groups.any { case ->
                                case?.value?.contains("برداشت") == true || case?.value?.contains("-") == true
                            }) TransactionType.WITHDRAW else TransactionType.DEPOSIT
                    }
                listOfModel.add(

                    SmsModel(
                        id = sms._id.toLong(),
                        bankName = if (split.first()
                                .isProbablyArabicOrPersian()
                        ) split.first().removeSpecialChar() else sms.senderName,
                        bankAccountNumber = finalBankAccountNumber.trim().removeFarsiChar(),
                        transactionType = transactionType,
                        transactionAmount = amount.removeFarsiChar().trim(),
                        transactionDate = sms.receiveDate ?: "-",
                        transactionTime = time ?: "-",
                        bankCardBalance = (split.find {
                            (it.contains("موجودی") ||
                                    it.contains("مانده") ||
                                    it.contains("موجودي"))
                        } ?: "-").removeFarsiChar(),
                        categoryIds = listOf(0L),
                        description = sms.description,
                        transactionDateTime = sms.receiveDateTime.toLong()
                    )
                )
            }

            return listOfModel.sortedByDescending { it.transactionDate }
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    private fun parsBankAccountNumber(smsSender: String, smsDescription: String): String {
        val smsSplitByNextLine = smsDescription.split("\n")
        smsSplitByNextLine.find {
            it.contains("برداشت از:") || it.contains("حساب:") || it.contains("واريز به") || it.contains(
                "حساب"
            )
        }?.let {
            if (it.isNotBlank()) {
                val t = it.split(":", " ")
                t.let { list ->
                    return list.find { item ->
                        item.any { char ->
                            char.isDigit()
                        }
                    }?.toString() ?: run {
                        return "حساب ناشناخته!"
                    }

                }

            } else
                return "حساب ناشناخته!"
        } ?: run {
            return ((if (smsSender == "B.QMEHRIRAN")
                smsSplitByNextLine.first()
            else if (smsSplitByNextLine.first().any { it.isDigit() }) {
                smsSplitByNextLine.first()
            } else {
                "حساب ناشناخته!"
            }).toString()
                    )
        }
        return "حساب ناشناخته!"

    }
}