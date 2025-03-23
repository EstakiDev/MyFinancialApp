package dev.estaki.myFinancialApp.presentation.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.estaki.domain.models.BankCardModel
import dev.estaki.domain.models.CategoryModel
import dev.estaki.domain.usecases.CacheCategoryToDb
import dev.estaki.domain.usecases.GetAllBankCardFromTbBankCard
import dev.estaki.domain.usecases.GetAllCategoryCount
import dev.estaki.domain.usecases.GetAllSmsByBankAccountNumber
import dev.estaki.domain.usecases.GetFirstOpenApp
import dev.estaki.domain.usecases.SaveFirstAppOpen
import dev.estaki.myFinancialApp.presentation.BottomSheetState
import dev.estaki.myFinancialApp.presentation.actions.MainScreenActions
import dev.estaki.myFinancialApp.presentation.actions.SplashScreenActions
import dev.estaki.myFinancialApp.presentation.states.MainScreenState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getAllSmsByBankAccountNumberUseCase: GetAllSmsByBankAccountNumber,
    private val getAllCategoryCount: GetAllCategoryCount,
    private val cacheCategoryToDb: CacheCategoryToDb,
    private val getFirstOpenApp: GetFirstOpenApp,
    private val saveFirstAppOpen: SaveFirstAppOpen,
    private val getAllBankCardFromTbBankCard: GetAllBankCardFromTbBankCard
) : ViewModel() {
    private val _mainScreenState = MutableStateFlow<MainScreenState>(MainScreenState())
    val mainScreenState = _mainScreenState.asStateFlow()

    private lateinit var listOfBankAccountNumber: List<BankCardModel>

    init {
        getAllCategory()
        if (getFirstOpenApp() <= 0) {
            saveFirstOpenApp(Date())
        }
    }

    fun onAction(action: MainScreenActions) {
        when (action) {
            is MainScreenActions.LoadSmsFromDb -> {
                viewModelScope.launch {
                    getAllBankCardFromTbBankCard.invoke().catch {
                        it.printStackTrace()
                    }.collect {
                        listOfBankAccountNumber = it
                        if (listOfBankAccountNumber.isNotEmpty())
                            getAllSmsByBankAccountNumber(listOfBankAccountNumber.first().bankAccountNumber)
                    }
                }
            }

            is MainScreenActions.OpenSms -> Unit
            is MainScreenActions.ReloadSmsByScrollCards -> {
                getAllSmsByBankAccountNumber(action.bankAccountNumber)
            }

        }
    }

    fun getAllSmsByBankAccountNumber(bankAccountNumber: String) {
        viewModelScope.launch {
            _mainScreenState.update { state ->
                state.copy(
                    isLoading = true,
                )
            }
            getAllSmsByBankAccountNumberUseCase.invoke(bankAccountNumber).catch {
                _mainScreenState.update { state ->
                    state.copy(
                        smsList = emptyList(),
                        isLoading = false,
                        isError = true,
                        errorMessage = it.message
                    )
                }
                it.printStackTrace()
            }.collect { smsList ->
                delay(1_000)
                _mainScreenState.update { state ->
                    state.copy(
                        smsList = smsList,
                        listBankAccountNumber = listOfBankAccountNumber,
                        isLoading = false,
                    )
                }
            }
        }
    }

    fun getBankAccountNumber() {
        viewModelScope.launch {
            getAllCategoryCount.invoke().catch {
                it.printStackTrace()
            }.collect { count ->
                if (count < 1)
                    addCategoryToDb()
            }
        }
    }

    fun getAllCategory() {
        viewModelScope.launch {
            getAllCategoryCount.invoke().catch {
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
        }.collect {
            Log.d("TAG", "addCategoryToDb: Success $it")
        }
    }

    private fun getFirstOpenApp(): Long {
        return getFirstOpenApp.invoke()
    }

    private fun saveFirstOpenApp(data: Date) {
        saveFirstAppOpen.invoke(data)
    }

}