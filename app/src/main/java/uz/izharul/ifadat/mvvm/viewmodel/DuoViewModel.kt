package uz.izharul.ifadat.mvvm.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import uz.izharul.ifadat.model.Dua
import uz.izharul.ifadat.model.DuaList
import uz.izharul.ifadat.mvvm.repository.DuaListRepository
import uz.izharul.ifadat.mvvm.repository.DuoRepository
import uz.izharul.ifadat.utils.*
import javax.inject.Inject

@HiltViewModel
@SuppressLint("StaticFieldLeak")
class DuoViewModel @Inject constructor(
    private val context: Context,
    private val repository: DuoRepository
) : ViewModel() {

    private val _offlineMode = mutableStateOf(false)
    val offlineMode = _offlineMode

    private val _loading = mutableStateOf(false)
    val isLoading = _loading

    private val _data = mutableStateOf<Dua?>(null)
    val data = _data

    fun getData(id: Int) = viewModelScope.launch {

        _loading.value = true

        try {

            val response = repository.getFromDatabase(id)

            if (response == null) {
                if (isInternetHave(context)) {
                    _offlineMode.value = false
                    val newData = repository.getFromServer(id)
                    repository.saveToDatabase(newData.body()!!)
                } else {
                    _offlineMode.value = true
                }
            } else {
                if (isInternetHave(context)) {
                    _offlineMode.value = false
                    val newData = repository.getFromServer(id)
                    repository.saveToDatabase(newData.body()!!)
                }
            }

            val data = repository.getFromDatabase(id)
            _data.value = data
            _loading.value = false
        } catch (e: Exception) {
            e.printStackTrace()
            isLoading.value = false
        }

    }

}