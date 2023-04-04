package uz.izharul.ifadat.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.gson.Gson
import uz.izharul.ifadat.R
import uz.izharul.ifadat.model.BobInfo
import java.util.*

@Composable
fun SetSystemColor() {
    // Remember a SystemUIController
    val systemUIController = rememberSystemUiController()
    val useDarkIcons = !isDarkMode()

    DisposableEffect(systemUIController, useDarkIcons) {
        // Update all of the system bar colors to be transparent, and use
        // dark icons if we're in light theme

        systemUIController.setSystemBarsColor(
            color = if (useDarkIcons) Color(0xFFFFFFFF) else Color(0xFF232323),
            darkIcons = useDarkIcons
        )

        // setStatusBarColor and setNavigationBarColor also exist

        onDispose {}
    }
}

@Composable
fun isDarkMode(): Boolean {
    val context = LocalContext.current
    return SharedDatabase(context).getBoolean("isDarkMode")
}

fun changeMode(context: Context, isDarkMode: Boolean) {
    SharedDatabase(context).saveBoolean("isDarkMode", isDarkMode)
}

val interRegular = Font(R.font.inter_regular)
val poppinsMedium = Font(R.font.poppins_medium)
val poppinsRegular = Font(R.font.poppins_regular)

fun audioTime(second: Int): String {
    var timerString = ""
    var secondString = ""

    val hours: Int = second / 3600
    val minutes: Int = second / 60
    val seconds: Int = second - (minutes * 60)

    if (hours > 0) {
        timerString = "$hours:"
    }

    secondString = if (seconds < 10) {
        "0$seconds"
    } else {
        "$seconds"
    }

    timerString = "$timerString$minutes:$secondString"
    return timerString

}

fun loadLocale(context: Context) {
    val language = SharedDatabase(context).getLanguage()
    val locale = Locale(language ?: "eng")
    Locale.setDefault(locale)
    val resources = context.resources
    val configuration = resources.configuration
    configuration.setLocale(locale)
    resources.updateConfiguration(configuration, resources.displayMetrics)
}

fun isLanLatin(context: Context): Boolean {
    val lan = SharedDatabase(context).getLanguage()

    return lan == "uz"
}

fun isInternetHave(context: Context): Boolean {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = connectivityManager.activeNetwork ?: return false
    val networkCapabilities = connectivityManager.getNetworkCapabilities(network) ?: return false

    return networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
}

fun List<BobInfo>.toJsonString(): String {
    return Gson().toJson(this)
}

fun String.toBobInfoList(): List<BobInfo> {
    return Gson().fromJson(this, Array<BobInfo>::class.java).toList()
}

@ExperimentalPermissionsApi
fun PermissionState.isPermanentlyDenied(): Boolean {
    return !shouldShowRationale && !hasPermission
}