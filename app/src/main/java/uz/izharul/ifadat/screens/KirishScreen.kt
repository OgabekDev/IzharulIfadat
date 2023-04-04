package uz.izharul.ifadat.screens

import android.app.AlertDialog
import android.widget.TextView
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
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
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.android.material.textview.MaterialTextView
import uz.izharul.ifadat.R
import uz.izharul.ifadat.model.KirishIzoh
import uz.izharul.ifadat.mvvm.viewmodel.KirishViewModel
import uz.izharul.ifadat.navigation.Graph
import uz.izharul.ifadat.ui.theme.BrandColor
import uz.izharul.ifadat.utils.*

@Composable
fun KirishScreen(
    navController: NavHostController
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        KirishView(navController = navController)
    }
}

@Composable
fun KirishView(
    navController: NavHostController,
    viewModel: KirishViewModel = hiltViewModel()
) {

    LaunchedEffect(true) {
        viewModel.getData()
    }

    var isDicClicked by remember {
        mutableStateOf(false)
    }

    BackHandler {
        if (isDicClicked) {
            isDicClicked = false
        } else {
            navController.popBackStack()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        if (viewModel.isLoading.value) {
            Loading(modifier = Modifier)
        } else if (viewModel.data.value != null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Transparent)
            ) {
                TopBar(
                    title = stringResource(id = R.string.kirish),
                    onBackClick = {
                        navController.popBackStack()
                    },
                    onSettingClick = {
                        navController.navigate(Graph.SETTING)
                    },
                    onDicClick = {
                        isDicClicked = !isDicClicked
                    },
                    isBack = true,
                    isSetting = true,
                    isDic = viewModel.data.value!!.dicsUz.isNotEmpty()
                )

                val context = LocalContext.current

                val dicsText = if (isLanLatin(context)) viewModel.data.value!!.dicsUz else viewModel.data.value!!.textOz

                val spannedText = HtmlCompat.fromHtml(dicsText, 0)
                val textColor = if (isDarkMode()) R.color.white else R.color.black
                LazyColumn(
                    modifier = Modifier

                ) {
                    item {
                        AndroidView(
                            modifier = Modifier
                                .padding(horizontal = 20.dp)
                                .padding(top = 20.dp)
                                .fillMaxWidth()
                                .clickable(
                                    interactionSource = MutableInteractionSource(),
                                    indication = null
                                ) {
                                    isDicClicked = false
                                },
                            factory = {
                                TextView(it).apply {
                                    setTextColor(resources.getColor(textColor))
                                    textSize = 18f
                                }
                            },
                            update = {
                                it.text = spannedText
                            }
                        )
                    }
                    item {
                        ProButton(
                            title = stringResource(id = R.string.izoh),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp)
                                .padding(bottom = 20.dp)
                                .height(60.dp)
                        ) {
                            navController.currentBackStackEntry?.savedStateHandle?.set(
                                "data", viewModel.data.value!!
                            )
                            navController.navigate(Graph.IZOH)
                        }
                    }
                }
            }
            if (isDicClicked) {
                Column(
                    modifier = Modifier
                        .padding(top = 70.dp)
                ) {
                    DicsSection(dics = viewModel.data.value!!.dicsUz)
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

@Composable
fun ProButton(
    title: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Surface(
        modifier = modifier
            .clickable {
                onClick()
            },
        shape = RoundedCornerShape(20.dp),
        color = BrandColor
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal,
                fontFamily = FontFamily(poppinsMedium),
                color = Color.White,
                modifier = Modifier
                    .padding(vertical = 10.dp)
            )
        }
    }
}