package dev.estaki.myFinancialApp.presentation

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.estaki.data.entities.SmsRawModel
import dev.estaki.domain.models.SmsModel
import dev.estaki.domain.models.TransactionType
import dev.estaki.domain.usecases.CacheSmsToDb
import dev.estaki.domain.usecases.GetAllSms
import dev.estaki.myFinancialApp.isProbablyArabicOrPersian
import dev.estaki.myFinancialApp.removeSpecialChar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val cashSmsToDb: CacheSmsToDb,
    private val getAllSms: GetAllSms,
) : ViewModel() {

    val smsLiveDataList = MutableLiveData<MutableList<SmsModel>>(mutableListOf())
    val isLoading = MutableLiveData<Boolean>()
    val viewState = MutableLiveData<ViewState>()
    suspend fun filterSmsData(smsL: ArrayList<SmsRawModel>) {
        viewModelScope.launch {

            withContext(Dispatchers.IO) {
                val list = smsL.filter { sms ->
                    (sms.description.contains("واریز") ||
                            sms.description.contains("واريز") ||
                            sms.description.contains("واریز به") ||
                            sms.description.contains("واریز حقوق") ||
                            sms.description.contains("برداشت") ||
                            sms.description.contains("برداشت از") ||
                            sms.description.contains("+") ||
                            sms.description.contains("-")
                            )
                            &&
                            (sms.description.contains("موجودی") ||
                                    sms.description.contains("مانده") ||
                                    sms.description.contains("موجودي") ||
                                    sms.description.contains("حساب"))
                }

                /** filter private number as Sender */
                val filterWithSender = list.filter {
                    !it.senderName.startsWith("+989") && !it.senderName.startsWith("98") && !it.senderName.startsWith(
                        "+98"
                    )
                }
                parseSmsToModel(filterWithSender)
            }

        }
    }


    private suspend fun parseSmsToModel(smsList: List<SmsRawModel>) {
        withContext(Dispatchers.IO) {
            val listOfModel = mutableListOf<SmsModel>()
            try {
                var i = 1L
                smsList.forEach { sms ->
                    val dateRegex =
                        Regex("\\d{2,4}+\\/\\d{1,2}\\/\\d{2,4}|([0-1]?[0-9]|2[0-3])\\/[0-5][0-9]")
                    val dateRegexMatch = dateRegex.find(sms.description)
                    val date = dateRegexMatch?.groups?.first()?.value

                    val timeRegex = Regex("([0-9]{2}+)(:[0-9]{2}+)(:[0-9]{2})*")
                    val timeRegexMatch = timeRegex.find(sms.description)
                    val time = timeRegexMatch?.groups?.first()?.value

                    val split = sms.description.split("\n")
                    var finalBankAccountNumber: String = ""
                    var splitBankAccountNumber: List<Any>? = null
                    (split.find {
                        it.contains("برداشت از:") || it.contains("حساب:") || it.contains(
                            "واریز به:"
                        )
                    }.let {
                        if (!it.isNullOrBlank()) {
                            splitBankAccountNumber = it.split(":")
                            if ((splitBankAccountNumber as List<*>).isNotEmpty()) {
                                finalBankAccountNumber =
                                    (splitBankAccountNumber as List<*>).last().toString()
                            }
                        }
                    })
                    listOfModel.add(
                        SmsModel(
                            id = i++,
                            bankName = if (split.first()
                                    .isProbablyArabicOrPersian()
                            ) split.first().removeSpecialChar() else sms.senderName,
                            bankAccountNumber = finalBankAccountNumber,
                            transactionType = if (!split.find {
                                    it.contains("برداشت") || it.contains(
                                        "-"
                                    )
                                }
                                    .isNullOrBlank()) TransactionType.WITHDRAW else TransactionType.DEPOSIT,
                            transactionAmount = (split.find {
                                it.contains("مبلغ") || it.contains(
                                    "برداشت"
                                ) || it.contains("واریز")
                                        || it.contains("-")
                                        || it.contains("+")
                            } ?: "-").split(":", "+", "-").last(),
                            transactionDate = date ?: "-",
                            transactionTime = time ?: "-",
                            bankCardBalance = split.find {
                                (it.contains("موجودی") ||
                                        it.contains("مانده") ||
                                        it.contains("موجودي"))
                            } ?: "-",
                            categoryId = 0L
                        )
                    )
                }

                launch {
                    cashSmsToDb.invoke(listOfModel)
                    Log.d("TAG", "parseSmsToModel: cashSmsToDb done")
                }.join()
                delay(4000)
                isLoading.postValue(false)
                viewState.postValue(ViewState.FINISH_SPLASH_ACTIVITY)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

    }

    fun getAllSms() {
        viewModelScope.launch {
            getAllSms.invoke().catch {
                it.printStackTrace()
            }.collect { smsList ->
                isLoading.postValue(false)

                smsLiveDataList.postValue(smsList.toMutableList())

            }
        }

    }


}