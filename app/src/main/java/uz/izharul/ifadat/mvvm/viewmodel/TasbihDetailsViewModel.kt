package uz.izharul.ifadat.mvvm.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import uz.izharul.ifadat.model.Tasbih
import uz.izharul.ifadat.mvvm.repository.TasbihDetailsRepository
import javax.inject.Inject

@HiltViewModel
class TasbihDetailsViewModel @Inject constructor(
    private val repository: TasbihDetailsRepository
): ViewModel() {

    private val _loading = mutableStateOf(false)
    val isLoading = _loading

    fun saveTasbih(data: Tasbih) = viewModelScope.launch {
        _loading.value = true
        try {
            repository.saveTasbih(data)
            _loading.value = false
        } catch (e: Exception) {
            e.printStackTrace()
            _loading.value = false
        }
    }

}