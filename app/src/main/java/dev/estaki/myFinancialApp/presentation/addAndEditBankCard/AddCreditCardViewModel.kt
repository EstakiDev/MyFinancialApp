package dev.estaki.myFinancialApp.presentation.addAndEditBankCard

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.estaki.domain.usecases.DeleteBankCard
import dev.estaki.domain.usecases.GetSingleBankAccount
import dev.estaki.domain.usecases.UpsertBankCard
import dev.estaki.myFinancialApp.presentation.actions.AddCreditCardActions
import dev.estaki.myFinancialApp.presentation.states.AddAndEditBankAccountScreenState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddCreditCardViewModel @Inject constructor(
    private val getSingleBankAccount: GetSingleBankAccount,
    private val upsertBankCard: UpsertBankCard,
    private val deleteBankCard: DeleteBankCard,
) : ViewModel() {

    private val _cardState: MutableStateFlow<AddAndEditBankAccountScreenState> = MutableStateFlow(
        AddAndEditBankAccountScreenState())
    val cardState = _cardState.asStateFlow()

    fun onAction(action: AddCreditCardActions) {
        when (action) {
            is AddCreditCardActions.LoadCard -> {
                viewModelScope.launch {
                    _cardState.update {
                        it.copy(
                            isLoading = true
                        )
                    }
                    getSingleBankAccount.invoke(action.bankAccountNumber).catch {
                        it.printStackTrace()
                        delay(1_000)
                        _cardState.update {
                            it.copy(
                                isLoading = false,
                                isError = true,
                                errorMessage = "خطا در دریافت اطلاعات کارت"
                            )
                        }
                    }.collect { item ->
                        delay(1_000)
                        _cardState.update {
                            it.copy(
                                isLoading = false,
                                bankCardModel = item
                            )
                        }
                    }
                }
            }

            is AddCreditCardActions.SaveCard -> {
                viewModelScope.launch {
                    _cardState.update {
                        it.copy(
                            isLoading = true
                        )
                    }
                    upsertBankCard.invoke(action.bankCardModel)
                    delay(1_000)
                    _cardState.update {
                        it.copy(
                            isLoading = false
                        )
                    }
                }
            }

            is AddCreditCardActions.DeleteCard -> {
                viewModelScope.launch {
                    _cardState.update {
                        it.copy(
                            isLoading = true
                        )
                    }
                    deleteBankCard.invoke(action.bankCardModel).catch {
                        it.printStackTrace()
                    }.collect {
                        _cardState.update {
                            it.copy(
                                isLoading = false
                            )
                        }
                    }
                    delay(2000)

                }
            }
        }
    }

}