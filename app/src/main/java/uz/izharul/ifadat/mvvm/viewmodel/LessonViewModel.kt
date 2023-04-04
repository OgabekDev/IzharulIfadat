package uz.izharul.ifadat.mvvm.viewmodel

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import uz.izharul.ifadat.model.Lesson
import uz.izharul.ifadat.mvvm.repository.LessonRepository
import uz.izharul.ifadat.utils.isInternetHave
import javax.inject.Inject

@HiltViewModel
@SuppressLint("StaticFieldLeak")
class LessonViewModel @Inject constructor(
    private val context: Context,
    private val repository: LessonRepository
): ViewModel() {

    private val _loading = mutableStateOf(false)
    val isLoading = _loading

    private val _offlineMode = mutableStateOf(false)
    val offlineMode = _offlineMode

    private val _data = mutableStateOf<Lesson?>(null)
    val data = _data

    fun getData(id: Int) = viewModelScope.launch {
        _loading.value = true

        try {

            val response = repository.getFromDatabase(id)

            if (response == null) {
                if (isInternetHave(context)) {
                    _offlineMode.value = false
                    val data = repository.getFromServer(id)
                    repository.saveToDatabase(data.body()!!)
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
                                repository.saveToDatabase(newData.body()!!)
                            }
                        }
                    }
                }
            }

            val data = repository.getFromDatabase(id)
            _data.value = data
            _loading.value = false

        } catch (e: Exception) {
            e.printStackTrace()
            _loading.value
        }
    }


}