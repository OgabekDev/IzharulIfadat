package uz.izharul.ifadat.mvvm.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import uz.izharul.ifadat.model.KirishIzoh
import uz.izharul.ifadat.mvvm.repository.KirishRepository
import uz.izharul.ifadat.utils.isInternetHave
import javax.inject.Inject

@HiltViewModel
@SuppressLint("StaticFieldLeak")
class KirishViewModel
@Inject constructor(
    private val context: Context,
    private val repository: KirishRepository
): ViewModel() {

    private val _offlineMode = mutableStateOf(false)
    val offlineMode = _offlineMode

    private val _loading = mutableStateOf(true)
    val isLoading = _loading

    private val _data = mutableStateOf<KirishIzoh?>(null)
    val data = _data

    fun getData() = viewModelScope.launch {
        _loading.value = true

        try {
            val response = repository.getFromDatabase()

            if (response == null) {
                if (isInternetHave(context)) {
                    _offlineMode.value = false
                    val data = repository.getFromServer()
                    repository.saveToDatabase(data.body()!!)
                } else {
                    _offlineMode.value = true
                }
            } else {
                if (isInternetHave(context)) {
                    _offlineMode.value = false
                    val version = repository.getVersion()
                    if (version.isSuccessful) {
                        if (version.body()!!.version > response.version) {
                            val newData = repository.getFromServer()
                            if (newData.isSuccessful) {
                                repository.saveToDatabase(newData.body()!!)
                            }
                        }
                    }
                }
            }

            val data = repository.getFromDatabase()
            _data.value = data
            _loading.value = false
        } catch (e: Exception) {
            e.printStackTrace()
            _loading.value = false
        }

    }

}