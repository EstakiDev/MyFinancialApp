package dev.estaki.myFinancialApp.presentation.detailScreen


import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.MutableSnapshot
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.estaki.domain.models.CategoryModel
import dev.estaki.domain.models.SmsModel
import dev.estaki.domain.usecases.GetAllCategoryList
import dev.estaki.domain.usecases.GetSingleSms
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class DetailScreenViewModel @Inject constructor(
    private val getAllCategoryList: GetAllCategoryList,
    private val getSingleSmsUseCase: GetSingleSms
) : ViewModel() {

    val categoryList : MutableStateFlow<List<CategoryModel>> = MutableStateFlow(emptyList())

    val isLoading = MutableStateFlow(true)
    private var _sms: MutableStateFlow<SmsModel?> = MutableStateFlow(null)
    val sms: StateFlow<SmsModel?>
        get() = _sms.asStateFlow()

    init {
        viewModelScope.launch {
            Log.d("TAG", "Load category has been started: ")
            getCategoryList()
            Log.d("TAG", "load category has been finished: ")
        }
    }

    fun loadSmsById(id: Long) {
        viewModelScope.launch {
            getSingleSmsUseCase.invoke(id).catch {
                it.printStackTrace()
            }.collect {
                _sms.value = it
            }
        }
    }

    private suspend fun getCategoryList() {
        getAllCategoryList.invoke().catch {
            Log.d("TAG", "getCategoryList: ")
            it.printStackTrace()
        }.collect { items ->
            Log.d("TAG", "getCategoryList: ")

            categoryList.emit(items)
            isLoading.value = false
        }

    }
}