package uz.izharul.ifadat.screens

import android.app.AlertDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import uz.izharul.ifadat.R
import uz.izharul.ifadat.model.Chapter
import uz.izharul.ifadat.mvvm.viewmodel.ChapterViewModel
import uz.izharul.ifadat.navigation.Graph
import uz.izharul.ifadat.utils.*

@Composable
fun ChapterScreen(
    navController: NavHostController,
    id: Int?
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        ChapterView(navController, id ?: 1)
    }
}

@Composable
fun ChapterView(
    navController: NavHostController,
    id: Int,
    viewModel: ChapterViewModel = hiltViewModel()
) {

    LaunchedEffect(true) {
        viewModel.getData(id)
    }

    val context = LocalContext.current

    Box {
        if (viewModel.isLoading.value) {
            Loading(modifier = Modifier)
        } else if (viewModel.data.value != null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Transparent)
            ) {
                TopBar(
                    title = if (isLanLatin(context)) viewModel.data.value!!.titleUz else viewModel.data.value!!.titleOz,
                    onBackClick = {
                        navController.popBackStack()
                    },
                    onSettingClick = {
                        navController.navigate(Graph.SETTING)
                    },
                    isBack = true,
                    isSetting = true
                )

                LazyColumn {
                    var count = 0
                    viewModel.data.value!!.lessons.forEach {
                        item(it) {
                            ChapterItem(
                                number = ++count,
                                onClick = {
                                    navController.currentBackStackEntry?.savedStateHandle?.set(
                                        "id", it.id
                                    )
                                    navController.navigate(Graph.LESSON)
                                }
                            )
                        }
                    }
                }

            }
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
        }
    }
}