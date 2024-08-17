package dev.estaki.myFinancialApp.presentation.detailScreen


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.estaki.domain.models.CategoryModel
import dev.estaki.domain.usecases.GetAllCategoryCount
import dev.estaki.domain.usecases.GetAllCategoryList
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailScreenViewModel @Inject constructor(private val getAllCategoryList: GetAllCategoryList): ViewModel() {

    val showLoading:MutableLiveData<Boolean> = MutableLiveData()
    val categoryListLiveData = MutableLiveData<List<CategoryModel>>()

    init {
        getCategoryList()
    }

    private fun getCategoryList(){
        viewModelScope.launch {
            showLoading.value = true
            getAllCategoryList.invoke().catch {
                it.printStackTrace()
            }.collect{ items ->
                categoryListLiveData.value = items
                showLoading.value = false
            }
        }
    }
}