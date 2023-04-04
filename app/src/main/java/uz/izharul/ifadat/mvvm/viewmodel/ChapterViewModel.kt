package uz.izharul.ifadat.mvvm.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import uz.izharul.ifadat.model.Bob
import uz.izharul.ifadat.model.BobFromDatabase
import uz.izharul.ifadat.mvvm.repository.ChapterRepository
import uz.izharul.ifadat.utils.*
import javax.inject.Inject

@HiltViewModel
@SuppressLint("StaticFieldLeak")
class ChapterViewModel @Inject constructor(
    private val context: Context,
    private val repository: ChapterRepository
) : ViewModel() {

    private val _offlineMode = mutableStateOf(false)
    val offlineMode = _offlineMode

    private val _loading = mutableStateOf(false)
    val isLoading = _loading

    private val _data = mutableStateOf<Bob?>(null)
    val data = _data

    fun getData(id: Int) = viewModelScope.launch {

        _loading.value = true

        try {

            val response = repository.getFromDatabase(id)

            if (response == null) {
                if (isInternetHave(context)) {
                    _offlineMode.value = false
                    val newData = repository.getFromServer(id)
                    repository.saveToDatabase(BobFromDatabase(newData.body()!!.id, newData.body()!!.titleUz, newData.body()!!.titleOz, newData.body()!!.lessons.toJsonString(), newData.body()!!.version))
                } else {
                    _offlineMode.value = true
                }
            } else {
                if (isInternetHave(context)) {
                    _offlineMode.value = false
                    val version = repository.getVersion(id)
                    if (version.isSuccessful) {
                        if (version.body()!!.version > response.version) {
                            val newData = repository.getFromServer(id)
                            if (newData.isSuccessful) {
                                repository.saveToDatabase(BobFromDatabase(newData.body()!!.id, newData.body()!!.titleUz, newData.body()!!.titleOz, newData.body()!!.lessons.toJsonString(), newData.body()!!.version))
                            }
                        }
                    }
                }
            }

            val data = repository.getFromDatabase(id)
            if (data != null) {
                _data.value = Bob(data.id, data.version, data.titleUz, data.titleOz, data.lessons.toBobInfoList())
            } else {
                _data.value = null
            }
            _loading.value = false
        } catch (e: Exception) {
            e.printStackTrace()
            isLoading.value = false
        }

    }

}