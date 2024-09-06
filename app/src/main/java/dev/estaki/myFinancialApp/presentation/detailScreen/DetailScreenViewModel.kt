package dev.estaki.myFinancialApp.presentation.detailScreen


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.estaki.domain.models.CategoryModel
import dev.estaki.domain.usecases.GetAllCategoryList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailScreenViewModel @Inject constructor(private val getAllCategoryList: GetAllCategoryList): ViewModel() {

    val categoryListLiveData = MutableLiveData<List<CategoryModel>>()
    val isLoading = MutableStateFlow(true)

    init {
        getCategoryList()
    }

    private fun getCategoryList(){
        viewModelScope.launch {
            getAllCategoryList.invoke().catch {
                it.printStackTrace()
            }.collect{ items ->
                categoryListLiveData.value = items
                isLoading.value = false
            }
        }
    }
}