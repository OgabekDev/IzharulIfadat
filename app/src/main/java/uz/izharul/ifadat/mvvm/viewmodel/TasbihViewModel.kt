package uz.izharul.ifadat.mvvm.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import uz.izharul.ifadat.model.Tasbih
import uz.izharul.ifadat.mvvm.repository.TasbihRepository
import uz.izharul.ifadat.utils.UiStateList
import uz.izharul.ifadat.utils.UiStateObject
import javax.inject.Inject

@HiltViewModel
class TasbihViewModel @Inject constructor(
    private val repository: TasbihRepository
): ViewModel() {

    private val _loading = mutableStateOf(false)
    val isLoading = _loading

    private val _list = mutableStateOf<List<Tasbih>>(listOf())
    val list = _list

    fun getList() = viewModelScope.launch {
        _loading.value = true
        try {
            val response = repository.getList()
            _list.value = response
            _loading.value = false
        } catch (e: Exception) {
            e.printStackTrace()
            _list.value = listOf()
            _loading.value = false
        }
    }
    fun create(data: Tasbih) = viewModelScope.launch {
        _loading.value = true
        try {
            repository.create(data)
            getList()
        } catch (e: Exception) {
            _loading.value = false
        }
    }

    fun delete(data: Tasbih) = viewModelScope.launch {
        _loading.value = true
        try {
            repository.delete(data)
            getList()
        } catch (e: Exception) {
            _loading.value = false
        }
    }

}