package uz.izharul.ifadat.screens

import android.Manifest
import android.media.MediaPlayer
import android.os.Environment
import android.widget.TextView
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavHostController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import kotlinx.coroutines.delay
import uz.izharul.ifadat.R
import uz.izharul.ifadat.mvvm.viewmodel.LessonViewModel
import uz.izharul.ifadat.mvvm.viewmodel.MusicViewModel
import uz.izharul.ifadat.navigation.Graph
import uz.izharul.ifadat.ui.theme.BrandColor
import uz.izharul.ifadat.ui.theme.NegativeColor
import uz.izharul.ifadat.utils.*
import android.app.AlertDialog
import uz.izharul.ifadat.mvvm.viewmodel.DuoViewModel

@Composable
fun DuoScreen(
    navController: NavHostController,
    id: Int?
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LessonBackground()
        DuoView(navController, id ?: 1)
    }
}

@Composable
fun DuoView(
    navController: NavHostController,
    id: Int,
    viewModel: DuoViewModel = hiltViewModel()
) {


    var isDicClicked by remember {
        mutableStateOf(false)
    }

    BackHandler {
        if (isDicClicked) {
            isDicClicked = false
        } else {
            if (mediaPlayer != null) {
                mediaPlayer!!.release()
                mediaPlayer = null
            }
            navController.popBackStack()
        }
    }

    LaunchedEffect(true) {
        viewModel.getData(id)
    }

    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent)
    ) {
        if (viewModel.isLoading.value) {
            Loading(modifier = Modifier)
        } else if (viewModel.data.value != null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Transparent)
                    .padding(bottom = 100.dp)
            ) {
                TopBar(
                    title = if (isLanLatin(context)) viewModel.data.value!!.titleUz else viewModel.data.value!!.titleOz,
                    onBackClick = {
                        if (mediaPlayer != null) {
                            mediaPlayer!!.release()
                            mediaPlayer = null
                        }
                        navController.popBackStack()
                    },
                    onSettingClick = {
                        if (mediaPlayer != null) {
                            mediaPlayer!!.release()
                            mediaPlayer = null
                        }
                        navController.navigate(Graph.SETTING)
                    },
                    onDicClick = {
                        isDicClicked = !isDicClicked
                    },
                    isBack = true,
                    isSetting = true,
                    isDic = viewModel.data.value!!.dicsUz.isNotEmpty()
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    val text =
                        if (isLanLatin(context)) viewModel.data.value!!.textUz else viewModel.data.value!!.textOz

                    val spannedText = HtmlCompat.fromHtml(text, 0)
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
                    }
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Transparent),
                contentAlignment = Alignment.BottomCenter
            ) {
                val title =
                    if (isLanLatin(context)) viewModel.data.value!!.titleUz else viewModel.data.value!!.titleOz
                AudioSection(
                    id = viewModel.data.value!!.id,
                    time = viewModel.data.value!!.audioTime,
                    title = title,
                    link = viewModel.data.value!!.audioLink,
                    type = "dua"
                )
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