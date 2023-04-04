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

var mediaPlayer: MediaPlayer? = null

@Composable
fun LessonScreen(
    navController: NavHostController,
    id: Int?
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LessonBackground()
        LessonView(navController, id ?: 1)
    }
}

@Composable
fun LessonView(
    navController: NavHostController,
    id: Int,
    viewModel: LessonViewModel = hiltViewModel()
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
                    type = "lesson"
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

@Composable
fun AudioSection(
    id: Int,
    time: Int,
    title: String,
    link: String,
    viewModel: MusicViewModel = hiltViewModel(),
    type: String
) {

    LaunchedEffect(true) {
        if (mediaPlayer != null) mediaPlayer!!.release()
    }

    var isPlaying by remember {
        mutableStateOf(mediaPlayer?.isPlaying ?: false)
    }

    var sliderPosition by remember {
        mutableStateOf(0f)
    }

    val context = LocalContext.current

    fun playMusic() {
        isPlaying = if (isPlaying) {
            if (mediaPlayer != null) {
                if (mediaPlayer!!.isPlaying) {
                    mediaPlayer!!.pause()
                }
            }
            false
        } else {
            try {
                if (mediaPlayer == null) {
                    mediaPlayer = MediaPlayer()
                    mediaPlayer!!.setDataSource(
                        context.getExternalFilesDir(
                            ".audios/$type"
                        ).toString() + "/${id}.mp3"
                    )
                    mediaPlayer!!.prepareAsync()
                    mediaPlayer!!.setOnPreparedListener {
                        it.start()
                    }
                    mediaPlayer?.setOnCompletionListener {
                        sliderPosition = 0f
                        isPlaying = false
                        mediaPlayer?.release()
                        mediaPlayer = null
                    }
                } else {
                    mediaPlayer!!.start()
                }
            } catch (e: Error) {
                e.printStackTrace()
            }
            true
        }
    }

    fun onClick() {

        if (viewModel.checkMusicIsHave(id, type)) {
            playMusic()
        } else {
            if (isInternetHave(context)) {
                viewModel.downloadMusic(id, link, type)
            } else {
                val builder = AlertDialog.Builder(context)
                builder.setMessage(R.string.err_internet)
                builder.setPositiveButton("OK") { dialog, _ ->
                    dialog.cancel()
                }
                builder.show()
            }
        }
    }

    LaunchedEffect(viewModel.downloaded.value) {
        if (viewModel.downloaded.value) {
            playMusic()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .background(MaterialTheme.colors.primary),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = if (viewModel.isDownloading.value && !viewModel.downloaded.value && viewModel.downloadStatus.value < 99) "$title     ${
                stringResource(
                    id = R.string.downloading
                )
            }   ${viewModel.downloadStatus.value}%" else title,
            fontFamily = FontFamily(poppinsMedium),
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            modifier = Modifier
                .padding(top = 20.dp, start = 20.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(75.dp)
                .background(Color.Transparent)
                .padding(top = 10.dp, bottom = 10.dp, start = 20.dp, end = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                onClick = {
                    onClick()
                },
                modifier = Modifier
                    .size(50.dp, 50.dp)
                    .shadow(0.dp)
            ) {
                Image(
                    painter = painterResource(id = if (isPlaying) R.drawable.ic_pause else R.drawable.ic_play),
                    contentDescription = "Play or Pause Music",
                    modifier = Modifier
                        .fillMaxSize(),
                    contentScale = ContentScale.Fit
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth(.8f)
            ) {
                Slider(
                    value = sliderPosition,
                    onValueChange = {
                        sliderPosition = it
                    },
                    valueRange = 0F..time.toFloat(),
                    colors = SliderDefaults.colors(
                        thumbColor = BrandColor,
                        disabledThumbColor = BrandColor,
                        activeTrackColor = BrandColor,
                        inactiveTrackColor = NegativeColor,

                        ),
                    onValueChangeFinished = {
                        seekTo(mediaPlayer, sliderPosition.toInt())
                    }
                )
            }

            Row(
                modifier = Modifier
                    .width(100.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = audioTime(time - sliderPosition.toInt()),
                    fontFamily = FontFamily(poppinsRegular),
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
                    modifier = Modifier
                        .padding(top = 0.dp, start = 0.dp)
                )
            }

        }
    }

    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {

        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_DESTROY) {
                if (mediaPlayer != null) {
                    mediaPlayer!!.release()
                    mediaPlayer = null
                    isPlaying = false
                }
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    LaunchedEffect(sliderPosition, isPlaying) {
        if (isPlaying) {
            delay(1000L)
            if (sliderPosition >= time) {
                sliderPosition = 0f
            } else {
                sliderPosition += 1L
            }
        }
    }

}

fun seekTo(mediaPlayer: MediaPlayer?, seconds: Int) {
    if (mediaPlayer != null) {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.seekTo(seconds * 1000)
        } else {
            mediaPlayer.start()
            mediaPlayer.seekTo(seconds * 1000)
            mediaPlayer.pause()
        }
    }
}