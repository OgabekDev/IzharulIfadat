package uz.izharul.ifadat.screens.main

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import uz.izharul.ifadat.R
import uz.izharul.ifadat.navigation.Graph
import uz.izharul.ifadat.utils.HomeItem
import uz.izharul.ifadat.model.HomeItem
import uz.izharul.ifadat.utils.MainBackground
import uz.izharul.ifadat.utils.TopBar

@Composable
fun MainScreen(
    navController: NavHostController
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        MainBackground()
        MainView(navController)
    }
}

@Composable
fun MainView(
    navController: NavHostController
) {

    val activity = (LocalContext.current as? Activity)

    BackHandler {
        activity?.finish()
    }

    val homeItems = listOf(
        HomeItem(
            id = 0,
            icon = R.drawable.ic_welcome,
            title = stringResource(R.string.kirish)
        ),
        HomeItem(
            id = 1,
            icon = R.drawable.ic_moon,
            title = stringResource(R.string.akida)
        ),
        HomeItem(
            id = 2,
            icon = R.drawable.ic_handwash,
            title = stringResource(R.string.poklik)
        ),
        HomeItem(
            id = 3,
            icon = R.drawable.ic_pray,
            title = stringResource(R.string.nomoz)
        ),
        HomeItem(
            id = 4,
            icon = R.drawable.ic_give_money,
            title = stringResource(R.string.zakot)
        ),
        HomeItem(
            id = 5,
            icon = R.drawable.ic_fasting,
            title = stringResource(R.string.roza)
        ),
        HomeItem(
            id = 6,
            icon = R.drawable.ic_kabaa,
            title = stringResource(R.string.haj)
        ),
        HomeItem(
            id = 7,
            icon = R.drawable.ic_helping,
            title = stringResource(R.string.muomalat)
        ),
        HomeItem(
            id = 8,
            icon = R.drawable.ic_hand_heart,
            title = stringResource(R.string.qalb_vojiblari)
        ),
        HomeItem(
            id = 9,
            icon = R.drawable.ic_no_drinking,
            title = stringResource(R.string.gunohlar)
        ),
        HomeItem(
            id = 10,
            icon = R.drawable.ic_praying,
            title = stringResource(R.string.tavba)
        )
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(bottom = 60.dp)
            .background(Color.Transparent)
    ) {
        TopBar(
            title = stringResource(R.string.app_name),
            onSettingClick = {
                navController.navigate(Graph.SETTING)
            }
        )

        Column(
            modifier = Modifier
                .background(Color.Transparent)
                .fillMaxWidth()
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                content = {
                    items(homeItems.size) {index ->
                        HomeItem(
                            icon = painterResource(id = homeItems[index].icon),
                            title = homeItems[index].title,
                            modifier = Modifier
                                .padding(10.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .clickable {
                                    if (homeItems[index].id == 0) {
                                        navController.navigate(Graph.KIRISH)
                                    } else {
                                        navController.currentBackStackEntry?.savedStateHandle?.set(
                                            "id", homeItems[index].id
                                        )
                                        navController.navigate(Graph.CHAPTER)
                                    }
                                }
                        )
                    }
                },
                modifier = Modifier
                    .padding(bottom = 10.dp)
            )
        }


    }
}