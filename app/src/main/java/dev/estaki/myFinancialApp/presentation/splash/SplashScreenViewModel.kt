package dev.estaki.myFinancialApp.presentation.splash

import android.content.ContentResolver
import android.provider.Telephony
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.estaki.domain.error.MyCustomSnackBarType
import dev.estaki.domain.models.BankCardModel
import dev.estaki.domain.models.SmsModel
import dev.estaki.domain.models.SmsRawModel
import dev.estaki.domain.processor.SmsProcessor
import dev.estaki.domain.usecases.CacheAllBankAccountToDb
import dev.estaki.domain.usecases.CacheSmsToDb
import dev.estaki.domain.usecases.CountAllBankAccountInDb
import dev.estaki.domain.usecases.GetAllBankAccountNumberFromTbSms
import dev.estaki.domain.usecases.GetAllSms
import dev.estaki.myFinancialApp.convertToTime
import dev.estaki.myFinancialApp.presentation.actions.SplashScreenActions
import dev.estaki.myFinancialApp.presentation.states.SplashScreenState
import dev.estaki.ui_utils.SnackBarController
import dev.estaki.ui_utils.SnackBarEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val cashSmsToDb: CacheSmsToDb,
    private val getAllBankAccountNumberFromTbSms: GetAllBankAccountNumberFromTbSms,
    private val cacheAllBankAccountToDb: CacheAllBankAccountToDb,
    private val countAllBankAccountInDb: CountAllBankAccountInDb,
    private val getAllSms: GetAllSms
) : ViewModel() {
    private val _splashScreenState = MutableStateFlow(SplashScreenState())
    val splashScreenState = _splashScreenState.asStateFlow()

    fun onAction(action: SplashScreenActions) {
        when (action) {
            is SplashScreenActions.GetPermissions -> {
                _splashScreenState.update { it.copy(mustGetPermissions = true) }
            }

            is SplashScreenActions.ExtractSmsFromContentResolver -> {
                viewModelScope.launch {
                    if (_splashScreenState.value.mustGetPermissions)
                        _splashScreenState.update { it.copy(mustGetPermissions = false) }
                    readSms(action.contentResolver)
                }
            }

            SplashScreenActions.MustGoToMainScreen -> {
                _splashScreenState.update {
                    it.copy(
                        isLoading = false,
                        isFinishedAndGoToMainScreen = true
                    )
                }
            }
        }
    }


    private suspend fun readSms(contentResolver: ContentResolver) {
        withContext(Dispatchers.Default) {
            val smsList = ArrayList<SmsRawModel>()
            val cursor = contentResolver.query(
                Telephony.Sms.CONTENT_URI,
                null,
                null,
                null,
                Telephony.Sms.DEFAULT_SORT_ORDER
            )
            cursor?.let {
                if (it.moveToFirst()) {
                    do {
                        val address =
                            cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Sms.ADDRESS))
                        val body =
                            cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Sms.BODY))
                        val date =
                            cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Sms.DATE))
                        val id = cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Sms._ID))

                        smsList.add(SmsRawModel(id, address, body, date, date))
                    } while (cursor.moveToNext())
                    correctDate(smsList)
                    val list = SmsProcessor(smsList).execute()?.toMutableList() ?: emptyList()
                    cacheSmsToDb(list.toMutableList())
                    cacheBankAccountToDb()
                    onAction(SplashScreenActions.MustGoToMainScreen)
                }
                it.close()
            }

        }
    }

    private suspend fun cacheBankAccountToDb() {
        getAllBankAccountNumberFromTbSms.invoke().catch {
            it.printStackTrace()
            SnackBarController.sendEvent(
                SnackBarEvent(
                    "متاسفانه عملیات مورد نظر با خطا مواجه شد!",
                    type = MyCustomSnackBarType.ERROR
                )
            )
        }.collect { listOfNewBankAccount ->

            countAllBankAccountInDb.invoke().catch {
                it.printStackTrace()
                SnackBarController.sendEvent(
                    SnackBarEvent(
                        "متاسفانه عملیات مورد نظر با خطا مواجه شد!",
                        type = MyCustomSnackBarType.ERROR
                    )
                )
            }.collect { list ->
                val mutableListOfOldBankAccount = list.toMutableList()
                val mutableListOfNewBankAccount = listOfNewBankAccount.toMutableList()

                val newList = mutableListOfNewBankAccount.map { new ->
                    val cardNumber = mutableListOfOldBankAccount.find { item ->
                        item.bankAccountNumber == new.bankAccountNumber
                    }?.bankCardNumber
                    new.copy(
                        bankCardNumber = cardNumber,
                        isItFromSms = true
                    )
                }

                Timber.tag("TAG").d("getAllBankAccountNumberFromTbSms: done ")
                cacheAllBankAccountToDb.invoke(
                    newList
                ).catch {
                    it.printStackTrace()
                    SnackBarController.sendEvent(
                        SnackBarEvent(
                            "متاسفانه عملیات مورد نظر با خطا مواجه شد!",
                            type = MyCustomSnackBarType.ERROR
                        )
                    )
                }.collect {
                    Timber.tag("TAG").d("cacheBankAccountToDb: done")
                }

            }


        }
    }


    private suspend fun getSavedSmsInDb(): List<SmsModel> {
        var result = listOf<SmsModel>()
        getAllSms.invoke().catch {
            it.printStackTrace()
            SnackBarController.sendEvent(
                SnackBarEvent(
                    "متاسفانه عملیات مورد نظر با خطا مواجه شد!",
                    type = MyCustomSnackBarType.ERROR
                )
            )
        }.collect { smsList ->
            result = smsList
            Timber.tag("TAG").d("getSavedSmsInDb: done")
        }
        return result
    }

    private suspend fun cacheSmsToDb(smsList: MutableList<SmsModel>) {
        Timber.tag("TAG").d("parseSmsToModel --;;;")
        val smsListInDb = getSavedSmsInDb()
        smsList.removeAll(smsListInDb)

        cashSmsToDb.invoke(smsList).catch {
            it.printStackTrace()
            SnackBarController.sendEvent(
                SnackBarEvent(
                    "متاسفانه عملیات مورد نظر با خطا مواجه شد!",
                    type = MyCustomSnackBarType.ERROR
                )
            )
        }.collect {
            Timber.tag("TAG").d("parseSmsToModel: cashSmsToDb done $it")
        }
    }

    fun correctDate(smsL: ArrayList<SmsRawModel>) {
        smsL.forEach {
            it.receiveDate = it.receiveDate.convertToTime()
        }
    }
}