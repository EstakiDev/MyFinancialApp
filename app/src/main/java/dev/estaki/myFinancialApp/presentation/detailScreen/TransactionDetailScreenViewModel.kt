package dev.estaki.myFinancialApp.presentation.detailScreen


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.estaki.domain.models.CategoryModel
import dev.estaki.domain.models.SmsModel
import dev.estaki.domain.usecases.GetAllBankCardFromTbBankCard
import dev.estaki.domain.usecases.GetAllCategoryList
import dev.estaki.domain.usecases.GetSingleSms
import dev.estaki.domain.usecases.UpsertSms
import dev.estaki.myFinancialApp.presentation.actions.TransactionDetailScreenActions
import dev.estaki.myFinancialApp.presentation.states.TransactionDetailScreenState
import kotlinx.coroutines.delay
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
    private val getAllBankCardFromTbBankCard: GetAllBankCardFromTbBankCard
) : ViewModel() {

    private var _state: MutableStateFlow<TransactionDetailScreenState> = MutableStateFlow(TransactionDetailScreenState())
    val state: StateFlow<TransactionDetailScreenState> = _state.asStateFlow()


    fun onAction(action: TransactionDetailScreenActions){
        when(action){
            is TransactionDetailScreenActions.DeleteTransaction -> {}
            is TransactionDetailScreenActions.LoadTransaction -> {
                viewModelScope.launch {
                    _state.update {
                        it.copy(
                            isLoading = true
                        )
                    }
                    getCategoryList()
                    loadSmsById(action.smsId)
                    getAllBankCardFromTb()
                    _state.update {
                        it.copy(
                            isLoading = false
                        )
                    }
                }
            }
            is TransactionDetailScreenActions.SaveTransaction -> {
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
            }
        }
    }

    private suspend fun getAllBankCardFromTb() {
        getAllBankCardFromTbBankCard.invoke().catch {
                it.printStackTrace()
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
        }.collect { items ->
            _state.update {
                it.copy(
                    categoryList = items
                )
            }
        }

    }
}