package uz.izharul.ifadat.screens

import android.widget.TextView
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.android.material.textview.MaterialTextView
import uz.izharul.ifadat.R
import uz.izharul.ifadat.model.Audio
import uz.izharul.ifadat.model.KirishIzoh
import uz.izharul.ifadat.navigation.Graph
import uz.izharul.ifadat.utils.DicsSection
import uz.izharul.ifadat.utils.TopBar
import uz.izharul.ifadat.utils.isDarkMode
import uz.izharul.ifadat.utils.isLanLatin

@Composable
fun IzohScreen(
    navController: NavHostController,
    data: KirishIzoh?
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        if (data != null) {
            IzohView(
                navController = navController,
                data = data
            )
        }
    }
}

@Composable
fun IzohView(
    navController: NavHostController,
    data: KirishIzoh
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

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent)
        ) {
            TopBar(
                title = stringResource(id = R.string.izoh),
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
                isDic = data.dicsOz.isNotEmpty()
            )

            val context = LocalContext.current

            val dicsText = if (isLanLatin(context)) data.dicsUz else data.textOz

            val spannedText = HtmlCompat.fromHtml(dicsText, 0)
            val textColor = if (isDarkMode()) R.color.white else R.color.black
            LazyColumn(
                modifier = Modifier
                    .padding(bottom = 100.dp)
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

        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Bottom
        ) {
            AudioSection(
                id = data.id,
                time = data.audioTime,
                title = stringResource(id = R.string.izoh),
                link = data.audioLink,
                type = "izoh"
            )
        }

        if (isDicClicked) {
            Column(
                modifier = Modifier
                    .padding(top = 70.dp)
            ) {
                DicsSection(dics = data.dicsUz)
            }
        }

    }
}