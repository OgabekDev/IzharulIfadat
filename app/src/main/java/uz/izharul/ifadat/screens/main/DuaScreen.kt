package uz.izharul.ifadat.screens.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import uz.izharul.ifadat.R
import uz.izharul.ifadat.model.DuaList
import uz.izharul.ifadat.mvvm.viewmodel.DuaListViewModel
import uz.izharul.ifadat.navigation.Graph
import uz.izharul.ifadat.utils.DuaItem
import uz.izharul.ifadat.utils.Loading
import uz.izharul.ifadat.utils.TopBar
import uz.izharul.ifadat.utils.poppinsMedium

@Composable
fun DuaScreen(
    navController: NavHostController,
    viewModel: DuaListViewModel = hiltViewModel()
) {

    LaunchedEffect(true) {
        viewModel.getData()
    }

    if (viewModel.isLoading.value) {
        Loading(modifier = Modifier)
    } else if (viewModel.offlineMode.value) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.err_internet),
                fontFamily = FontFamily(poppinsMedium),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    } else if (viewModel.data.value.isNotEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(bottom = 70.dp)
                .background(Color.Transparent)
        ) {
            TopBar(
                title = stringResource(id = R.string.dualar),
                onSettingClick = {
                    navController.navigate(Graph.SETTING)
                }
            )

            Column(
                modifier = Modifier
                    .background(Color.Transparent)
                    .fillMaxWidth()
            ) {
                LazyColumn {
                    var count = 0
                    viewModel.data.value.forEach { dua ->
                        item(count++) {
                            DuaItem(
                                duoItem = dua,
                                onClick = {
                                    navController.currentBackStackEntry?.savedStateHandle?.set(
                                        "id", it
                                    )
                                    navController.navigate(Graph.DUO)
                                },
                                modifier = Modifier
                                    .padding(horizontal = 10.dp)
                                    .padding(top = if (dua.id == viewModel.data.value[0].id) 10.dp else 0.dp)
                                    .padding(bottom = 10.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}