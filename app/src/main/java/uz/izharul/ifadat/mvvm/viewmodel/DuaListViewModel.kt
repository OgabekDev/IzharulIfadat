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
import uz.izharul.ifadat.model.DuaList
import uz.izharul.ifadat.mvvm.repository.ChapterRepository
import uz.izharul.ifadat.mvvm.repository.DuaListRepository
import uz.izharul.ifadat.utils.*
import javax.inject.Inject

@HiltViewModel
@SuppressLint("StaticFieldLeak")
class DuaListViewModel @Inject constructor(
    private val context: Context,
    private val repository: DuaListRepository
) : ViewModel() {

    private val _offlineMode = mutableStateOf(false)
    val offlineMode = _offlineMode

    private val _loading = mutableStateOf(false)
    val isLoading = _loading

    private val _data = mutableStateOf<List<DuaList>>(emptyList())
    val data = _data

    fun getData() = viewModelScope.launch {

        _loading.value = true

        try {

            val response = repository.getFromDatabase()

            if (response == null || response.isEmpty()) {
                if (isInternetHave(context)) {
                    _offlineMode.value = false
                    val newData = repository.getFromServer()
                    newData.body()!!.forEach { duaList ->
                        repository.saveToDatabase(duaList)
                    }
                } else {
                    _offlineMode.value = true
                }
            } else {
                if (isInternetHave(context)) {
                    _offlineMode.value = false
                    val newData = repository.getFromServer()
                    repository.deleteAll()
                    newData.body()!!.forEach { duaList ->
                        repository.saveToDatabase(duaList)
                    }
                }
            }

            val data = repository.getFromDatabase()
            _data.value = data ?: emptyList()
            _loading.value = false
        } catch (e: Exception) {
            e.printStackTrace()
            isLoading.value = false
        }

    }

}