package uz.izharul.ifadat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import uz.izharul.ifadat.screens.HomeScreen
import uz.izharul.ifadat.ui.theme.MainTheme
import uz.izharul.ifadat.utils.*

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SetSystemColor()

            loadLocale(this)

            MainTheme(darkTheme = isDarkMode()) {

                HomeScreen(rememberNavController())

            }
        }
    }
}