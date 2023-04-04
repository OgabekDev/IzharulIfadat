package uz.izharul.ifadat.screens

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import uz.izharul.ifadat.R
import uz.izharul.ifadat.model.BottomNavItem
import uz.izharul.ifadat.navigation.Home
import uz.izharul.ifadat.navigation.HomeNavigation
import uz.izharul.ifadat.utils.BottomNavigationBar

@Composable
fun HomeScreen(
    navController: NavHostController
) {
    val route = navController.currentBackStackEntryAsState()

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                list = listOf(
                    BottomNavItem(
                        name = "MainScreen",
                        icon =  painterResource(id = R.drawable.ic_btm_1),
                        route = Home.MAIN
                    ),
                    BottomNavItem(
                        name = "DuaScreen",
                        icon =  painterResource(id = R.drawable.ic_btm_2),
                        route = Home.DUA
                    ),
                    BottomNavItem(
                        name = "TasbihScreen",
                        icon =  painterResource(id = R.drawable.ic_btm_3),
                        route = Home.TASBIH
                    )
                ),
                navController = navController,
                onItemClick = {
                    if (it.route != route.value?.destination?.route)
                        navController.navigate(it.route)
                },
                modifier = Modifier
                    .height(70.dp)
            )
        },
    ) {
        HomeNavigation(navController = navController)
        Row(
            modifier = Modifier
                .padding(it.calculateBottomPadding())
        ) {}
    }

}