package uz.izharul.ifadat.mvvm.viewmodel

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
@SuppressLint("StaticFieldLeak")
class MusicViewModel @Inject constructor(
    private val context: Context
) : ViewModel() {

    private val _downloadStatus = mutableStateOf(0)
    val downloadStatus = _downloadStatus

    private val _downloaded = mutableStateOf(false)
    val downloaded = _downloaded

    private val _downloading = mutableStateOf(false)
    val isDownloading = _downloading

    @SuppressLint("Range")
    fun downloadMusic(id: Int, link: String, type: String) {

        val storageDir = context.getExternalFilesDir(".audios/$type")
        if (!storageDir!!.exists()) {
            storageDir.mkdir()
        }

        val request = DownloadManager.Request(Uri.parse(link))
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN)
            .setDestinationInExternalFilesDir(context, ".audios/$type", "$id.mp3")
            .setAllowedOverMetered(true)
            .setAllowedOverRoaming(true)

        val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val downloadId = downloadManager.enqueue(request)

        val query = DownloadManager.Query().setFilterById(downloadId)
        CoroutineScope(Dispatchers.Default).launch {
            _downloading.value = true
            while (_downloading.value) {
                delay(100)
                val cursor = downloadManager.query(query)
                if (cursor.moveToFirst()) {
                    val downloadBytes =
                        cursor.getLong(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR))
                    val totalBytes =
                        cursor.getLong(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES))
                    val percent = (downloadBytes * 100L / totalBytes).toInt()
                    _downloadStatus.value = percent
                    if (DownloadManager.STATUS_SUCCESSFUL == cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))) {
                        _downloading.value = false
                        _downloaded.value = true
                    }
                }
                cursor.close()
            }
        }

    }

    fun checkMusicIsHave(id: Int, type: String): Boolean {
        val storageDir = context.getExternalFilesDir(".audios/$type")
        if (!storageDir!!.exists()) {
            storageDir.mkdir()
        }
        val file = File(storageDir, "$id.mp3")
        return file.exists()
    }

}