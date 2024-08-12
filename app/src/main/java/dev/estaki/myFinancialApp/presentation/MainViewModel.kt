package dev.estaki.myFinancialApp.presentation

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.estaki.data.entities.SmsRawModel
import dev.estaki.domain.models.CategoryModel
import dev.estaki.domain.models.SmsModel
import dev.estaki.domain.models.TransactionType
import dev.estaki.domain.usecases.CacheCategoryToDb
import dev.estaki.domain.usecases.CacheSmsToDb
import dev.estaki.domain.usecases.GetAllCategory
import dev.estaki.domain.usecases.GetAllSms
import dev.estaki.myFinancialApp.convertToTime
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
    private val getAllCategory: GetAllCategory,
    private val cacheCategoryToDb: CacheCategoryToDb
) : ViewModel() {

    val smsLiveDataList = MutableLiveData<MutableList<SmsModel>>(mutableListOf())
    val isLoading = MutableLiveData<Boolean>()
    val viewState = MutableLiveData<ViewState>()
    suspend fun filterSmsData(smsL: ArrayList<SmsRawModel>) {
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

        filterWithSender.forEach {
            Log.d("TAG", "readSms: date: ${it.receiveDate.convertToTime()}")
            it.receiveDate = it.receiveDate.convertToTime()
        }
        parseSmsToModel(filterWithSender)

    }


    private suspend fun parseSmsToModel(smsList: List<SmsRawModel>) {
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
                        transactionDate = sms.receiveDate ?: "-",
                        transactionTime = time ?: "-",
                        bankCardBalance = split.find {
                            (it.contains("موجودی") ||
                                    it.contains("مانده") ||
                                    it.contains("موجودي"))
                        } ?: "-",
                        categoryId = 0L,
                        description = null
                    )
                )
            }
            cashSmsToDb.invoke(listOfModel).catch {
                it.printStackTrace()
            }.collect{
                Log.d("TAG", "parseSmsToModel: cashSmsToDb done $it")

                delay(4000)
                isLoading.postValue(false)
                viewState.postValue(ViewState.FINISH_SPLASH_ACTIVITY)
            }

        } catch (e: Exception) {
            e.printStackTrace()
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

    fun getAllCategory() {
        viewModelScope.launch {
            getAllCategory.invoke().catch {
                it.printStackTrace()
            }.collect { count ->
                if (count < 1)
                    addCategoryToDb()
            }
        }
    }

    private suspend fun addCategoryToDb() {

        val categoryList = mutableListOf<CategoryModel>()
        categoryList.add(CategoryModel(1, "خرید خوراکی"))
        categoryList.add(CategoryModel(2, "خرید شارژ"))
        categoryList.add(CategoryModel(3, "بسته اینترنت"))
        categoryList.add(CategoryModel(4, "خودرو"))
        categoryList.add(CategoryModel(5, "لوازم جانبی موبایل"))
        categoryList.add(CategoryModel(6, "پوشاک"))
        categoryList.add(CategoryModel(7, "اجاره خانه"))
        categoryList.add(CategoryModel(8, "پرداخت قبض"))
        categoryList.add(CategoryModel(9, " هزینه سفر"))
        categoryList.add(CategoryModel(10, "بلیط سفر"))
        categoryList.add(CategoryModel(11, "تفریح با دوستان"))
        categoryList.add(CategoryModel(12, "شام بیرون از منزل"))
        categoryList.add(CategoryModel(13, "ناهار بیرون از منزل"))
        categoryList.add(CategoryModel(14, "ورزش و باشگاه"))
        categoryList.add(CategoryModel(15, "آرایشگاه و خدمات زیبایی"))
        categoryList.add(CategoryModel(16, "خرید لوازم آرایشی و بهداشتی"))
        categoryList.add(CategoryModel(17, "لوازم جانبی کامپیوتر و لپ تاپ"))
        categoryList.add(CategoryModel(18, "تاکسی اینترنتی"))
        categoryList.add(CategoryModel(19, "سفارش غذا"))
        categoryList.add(CategoryModel(20, "خرید سوپرمارکتی"))
        categoryList.add(CategoryModel(21, "دیت"))
        categoryList.add(CategoryModel(22, "سینما"))

        cacheCategoryToDb.invoke(categoryList).catch {
            it.printStackTrace()
        }.collect{
            Log.d("TAG", "addCategoryToDb: Success $it")
        }
    }

}