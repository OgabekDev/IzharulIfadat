package uz.izharul.ifadat.model

import androidx.compose.ui.graphics.painter.Painter

data class BottomNavItem(
    val name: String,
    val icon: Painter,
    val route: String
)
