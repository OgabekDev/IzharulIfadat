package uz.izharul.ifadat.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import uz.izharul.ifadat.model.KirishIzoh
import uz.izharul.ifadat.model.Tasbih
import uz.izharul.ifadat.screens.*
import uz.izharul.ifadat.screens.main.*

@Composable
fun HomeNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Home.MAIN
    ) {
        composable(Home.MAIN) {
            MainScreen(navController)
        }
        composable(Home.DUA) {
            DuaScreen(navController)
        }
        composable(Home.TASBIH) {
            TasbihScreen(navController)
        }
        composable(Home.QIBLAA) {
            QiblaaScreen(navController)
        }
        composable(Home.MORE) {
            MoreScreen(navController)
        }
        settingsScreen(navController)
        chapterScreen(navController)
        kirishScreen(navController)
        izohScreen(navController)
        lessonScreen(navController)
        tasbihDetailsScreen(navController)
        duoDetailsScreen(navController)
    }
}

fun NavGraphBuilder.settingsScreen(navController: NavHostController) {
    navigation(
        route = Graph.SETTING,
        startDestination = Screen.SETTING
    ) {
        composable(route = Screen.SETTING) {
            SettingsScreen(navController)
        }
    }
}

fun NavGraphBuilder.chapterScreen(navController: NavHostController) {
    navigation(
        route = Graph.CHAPTER,
        startDestination = Screen.CHAPTER
    ) {
        composable(route = Screen.CHAPTER) {
            val id = navController.previousBackStackEntry?.savedStateHandle?.get<Int>("id")
            ChapterScreen(navController, id)
        }
    }
}
fun NavGraphBuilder.kirishScreen(navController: NavHostController) {
    navigation(
        route = Graph.KIRISH,
        startDestination = Screen.KIRISH
    ) {
        composable(route = Screen.KIRISH) {
            KirishScreen(navController)
        }
    }
}
fun NavGraphBuilder.izohScreen(navController: NavHostController) {
    navigation(
        route = Graph.IZOH,
        startDestination = Screen.IZOH
    ) {
        composable(route = Screen.IZOH) {
            val data = navController.previousBackStackEntry?.savedStateHandle?.get<KirishIzoh>("data")
            IzohScreen(navController, data)
        }
    }
}

fun NavGraphBuilder.lessonScreen(navController: NavHostController) {
    navigation(
        route = Graph.LESSON,
        startDestination = Screen.LESSON
    ) {
        composable(route = Screen.LESSON) {
            val id = navController.previousBackStackEntry?.savedStateHandle?.get<Int>("id")
            LessonScreen(navController, id)
        }
    }
}

fun NavGraphBuilder.tasbihDetailsScreen(navController: NavHostController) {
    navigation(
        route = Graph.TASBIH,
        startDestination = Screen.TASBIH,
    ) {
        composable(
            route = Screen.TASBIH,
        ) {
            val tasbih =
                navController.previousBackStackEntry?.savedStateHandle?.get<Tasbih>("tasbih")
            TasbihDetailsScreen(navController, tasbih)
        }
    }
}

fun NavGraphBuilder.duoDetailsScreen(navController: NavHostController) {
    navigation(
        route = Graph.DUO,
        startDestination = Screen.DUO,
    ) {
        composable(
            route = Screen.DUO,
        ) {
            val id =
                navController.previousBackStackEntry?.savedStateHandle?.get<Int>("id")
            DuoScreen(navController, id)
        }
    }
}