package dev.estaki.myFinancialApp.presentation.detailScreen


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.estaki.domain.error.MyCustomSnackBarType
import dev.estaki.domain.models.SmsModel
import dev.estaki.domain.usecases.DeleteSms
import dev.estaki.domain.usecases.GetAllBankCardFromTbBankCard
import dev.estaki.domain.usecases.GetAllCategoryList
import dev.estaki.domain.usecases.GetSingleSms
import dev.estaki.domain.usecases.SetSmsWasSaw
import dev.estaki.domain.usecases.UpsertSms
import dev.estaki.myFinancialApp.presentation.actions.TransactionDetailScreenActions
import dev.estaki.myFinancialApp.presentation.states.TransactionDetailScreenState
import dev.estaki.ui_utils.SnackBarController
import dev.estaki.ui_utils.SnackBarEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionDetailScreenViewModel @Inject constructor(
    private val getAllCategoryList: GetAllCategoryList,
    private val getSingleSmsUseCase: GetSingleSms,
    private val saveSmsUseCase: UpsertSms,
    private val deleteSms: DeleteSms,
    private val setSmsWasSawUseCase: SetSmsWasSaw,
    private val getAllBankCardFromTbBankCard: GetAllBankCardFromTbBankCard
) : ViewModel() {

    private var _state: MutableStateFlow<TransactionDetailScreenState> = MutableStateFlow(TransactionDetailScreenState())
    val state: StateFlow<TransactionDetailScreenState> = _state.asStateFlow()


    fun onAction(action: TransactionDetailScreenActions){
        when(action){
            is TransactionDetailScreenActions.DeleteTransaction -> {
                viewModelScope.launch {
                    _state.update {
                        it.copy(
                            isLoading = true
                        )
                    }
                    deleteTransaction(action.smsModel)
                    _state.update {
                        it.copy(
                            isLoading = false
                        )
                    }
                }
            }
            is TransactionDetailScreenActions.LoadTransaction -> {
                viewModelScope.launch {
                    _state.update {
                        it.copy(
                            isLoading = true
                        )
                    }
                    getAllBankCardFromTb()
                    getCategoryList()
                    if (action.smsId != 0L){
                        loadSmsById(action.smsId)
                        setSmsWasSawUseCase.invoke(action.smsId)
                    }
                    _state.update {
                        it.copy(
                            isLoading = false
                        )
                    }
                }
            }
            is TransactionDetailScreenActions.SaveTransaction -> {
                viewModelScope.launch {
                    _state.update {
                        it.copy(
                            isLoading = true
                        )
                    }
                    saveSms(action.smsModel)
                    _state.update {
                        it.copy(
                            isLoading = false
                        )
                    }
                    SnackBarController.sendEvent(
                        SnackBarEvent(
                            "تغییرات مورد نظر شما ذخیره شد.",
                            type = MyCustomSnackBarType.SUCCESS
                        )
                    )
                }
            }
        }
    }

    private suspend fun deleteTransaction(smsModel: SmsModel) {
        deleteSms.invoke(smsModel).catch {
            it.printStackTrace()
            SnackBarController.sendEvent(
                SnackBarEvent(
                    "متاسفانه عملیات مورد نظر با خطا مواجه شد!",
                    type = MyCustomSnackBarType.ERROR
                )
            )
        }.collect {
            SnackBarController.sendEvent(
                SnackBarEvent(
                    "تغییرات مورد نظر شما ذخیره شد.",
                    type = MyCustomSnackBarType.SUCCESS
                )
            )
        }
    }
    private suspend fun getAllBankCardFromTb() {
        getAllBankCardFromTbBankCard.invoke().catch {
            it.printStackTrace()
            SnackBarController.sendEvent(
                SnackBarEvent(
                    "متاسفانه عملیات مورد نظر با خطا مواجه شد!",
                    type = MyCustomSnackBarType.ERROR
                )
            )
        }.collect {bankCardList ->
                _state.update {
                    it.copy(
                        bankCardList = bankCardList
                    )
                }
            }
    }
    private suspend fun loadSmsById(id: Long) {
            getSingleSmsUseCase.invoke(id).catch {
                it.printStackTrace()
                SnackBarController.sendEvent(
                    SnackBarEvent(
                        "متاسفانه عملیات مورد نظر با خطا مواجه شد!",
                        type = MyCustomSnackBarType.ERROR
                    )
                )
            }.collect {smsModel ->
                _state.update {
                    it.copy(
                        smsModel = smsModel
                    )
                }
            }
    }
    private suspend fun setSmsWasSaw(id: Long) {
        getSingleSmsUseCase.invoke(id).catch {
            it.printStackTrace()
            SnackBarController.sendEvent(
                SnackBarEvent(
                    "متاسفانه عملیات مورد نظر با خطا مواجه شد!",
                    type = MyCustomSnackBarType.ERROR
                )
            )
        }.collect {smsModel ->
            _state.update {
                it.copy(
                    smsModel = smsModel
                )
            }
        }
    }
    private fun saveSms(smsModel: SmsModel) {
        viewModelScope.launch {
            saveSmsUseCase.invoke(smsModel)
        }
    }

    private suspend fun getCategoryList() {
        getAllCategoryList.invoke().catch {
            it.printStackTrace()
            SnackBarController.sendEvent(
                SnackBarEvent(
                    "متاسفانه عملیات مورد نظر با خطا مواجه شد!",
                    type = MyCustomSnackBarType.ERROR
                )
            )
        }.collect { items ->
            _state.update {
                it.copy(
                    categoryList = items
                )
            }
        }

    }
}