package dev.estaki.domain.validator

import dev.estaki.domain.models.SmsRawModel

object SmsValidator {

    fun isBankSms(smsRawModel: SmsRawModel): Boolean {
        return assertOfSender(smsRawModel) && assertOfDescription(smsRawModel)
    }

    private fun assertOfSender(smsRawModel: SmsRawModel): Boolean {
        return smsRawModel.senderName.startsWith("+989").not() ||
                smsRawModel.senderName.startsWith("98").not() ||
                smsRawModel.senderName.startsWith("+98").not() ||
                smsRawModel.senderName.first().isDigit().not() ||
                smsRawModel.senderName.contains("HAMRAHAVAL", true).not() ||
                smsRawModel.senderName.contains("IRANCELL", true).not() ||
                smsRawModel.senderName.contains("RIGHTEL", true).not() ||
                smsRawModel.senderName.contains("DIGIPAY", true).not()

    }

    private fun assertOfDescription(smsRawModel: SmsRawModel): Boolean {

        return (
                        smsRawModel.description.contains("واریز") ||
                        smsRawModel.description.contains("واريز") ||
                        smsRawModel.description.contains("واریز به") ||
                        smsRawModel.description.contains("واریز حقوق") ||
                        smsRawModel.description.contains("برداشت") ||
                        smsRawModel.description.contains("برداشت از") ||
                        smsRawModel.description.contains("+") ||
                        smsRawModel.description.contains("-") ||
                        smsRawModel.description.contains("حساب")
                )
                &&
                (
                        smsRawModel.description.contains("مانده") ||
                        smsRawModel.description.contains("موجودی") ||
                        smsRawModel.description.contains("موجودي")
                )
                &&
                (
                        smsRawModel.description.contains("مشتری").not() ||
                        smsRawModel.description.contains("مشتر").not() ||
                        smsRawModel.description.contains("مشترک").not() ||
                        smsRawModel.description.contains("گرامی").not() ||
                        smsRawModel.description.contains("محترم").not() ||
                        smsRawModel.description.contains("جناب").not() ||
                        smsRawModel.description.contains("آقای").not() ||
                        smsRawModel.description.contains("خانم").not() ||
                        smsRawModel.description.contains("قرعه").not() ||
                        smsRawModel.description.contains("رمز").not() ||
                        smsRawModel.description.contains("طلایی").not()
                )

    }
}
