package uz.izharul.ifadat.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavHostController
import uz.izharul.ifadat.model.Tasbih
import uz.izharul.ifadat.mvvm.viewmodel.TasbihDetailsViewModel
import uz.izharul.ifadat.navigation.Graph
import uz.izharul.ifadat.ui.theme.BrandColor
import uz.izharul.ifadat.utils.*

@Composable
fun TasbihDetailsScreen(
    navController: NavHostController, tasbih: Tasbih?
) {
    if (tasbih != null) TasbihView(
        navController = navController, tasbih = tasbih
    )
}

@Composable
fun TasbihView(
    navController: NavHostController,
    tasbih: Tasbih,
    viewModel: TasbihDetailsViewModel = hiltViewModel()
) {

    var count by remember {
        mutableStateOf(tasbih.count)
    }

    val interactionSource = remember {
        MutableInteractionSource()
    }

    var color by remember {
        mutableStateOf(tasbih.color)
    }

    var isColorClicked by remember {
        mutableStateOf(false)
    }

    var isTypeClicked by remember {
        mutableStateOf(false)
    }

    var countLimit by remember {
        mutableStateOf(tasbih.countLimit)
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        TasbihDetailsBackground()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent)
        ) {
            TopBar(title = tasbih.title, onBackClick = {
                navController.popBackStack()
            }, onSettingClick = {
                navController.navigate(Graph.SETTING)
            }, isBack = true, isSetting = true
            )

            Box(
                modifier = Modifier
                    .background(Color.Transparent)
                    .padding(bottom = 70.dp)
            ) {
                if (viewModel.isLoading.value) {
                    Loading()
                } else {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Transparent)
                            .padding()
                    ) {

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.Transparent)
                                .padding(end = 20.dp)
                                .padding(top = 20.dp),
                            horizontalArrangement = Arrangement.End,
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Surface(
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .size(40.dp)
                                    .background(Color.Transparent)
                                    .clickable {
                                        isTypeClicked = false
                                        isColorClicked = !isColorClicked
                                    }, shape = CircleShape, color = Color(color)
                            ) {}

                            Spacer(modifier = Modifier.width(15.dp))

                            Surface(
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape)
                                    .background(Color.Transparent)
                                    .clickable {
                                        isColorClicked = false
                                        isTypeClicked = !isTypeClicked
                                    }, shape = CircleShape, color = MaterialTheme.colors.secondary
                            ) {
                                Surface(
                                    modifier = Modifier
                                        .size(40.dp)
                                        .padding(2.dp)
                                        .background(Color.Transparent),
                                    shape = CircleShape,
                                    color = MaterialTheme.colors.background
                                ) {
                                    Text(
                                        text = if (countLimit != 0) countLimit.toString() else "∞",
                                        fontFamily = FontFamily(poppinsRegular),
                                        fontWeight = FontWeight.Normal,
                                        fontSize = 20.sp,
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .padding(top = 3.dp),
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }

                        }

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 10.dp)
                                .padding(end = 20.dp)
                                .background(Color.Transparent),
                            horizontalAlignment = Alignment.End
                        ) {
                            if (isColorClicked) {
                                TasbihColors {
                                    isColorClicked = false
                                    color = it
                                }
                            }
                        }

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 0.dp)
                                .padding(end = 15.dp)
                                .background(Color.Transparent),
                            horizontalAlignment = Alignment.End
                        ) {
                            if (isTypeClicked) {
                                TasbihTypes(selected = countLimit) {
                                    isTypeClicked = false
                                    countLimit = it
                                }
                            }
                        }
                    }
                }
            }

        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent),
            contentAlignment = Alignment.Center,
        ) {
            Surface(shape = CircleShape,
                color = BrandColor,
                modifier = Modifier
                    .size(150.dp)
                    .padding(bottom = 7.dp)
                    .padding(end = 2.dp)
                    .clickable(
                        interactionSource = interactionSource, indication = null
                    ) {
                        isColorClicked = false
                        isTypeClicked = false
                        if (countLimit != 0) {
                            if (count >= countLimit) count = 1 else count++
                        } else {
                            count++
                        }
                    }) {
                Box(
                    modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (countLimit != 0) "${countLimit}/$count" else "$count",
                        modifier = Modifier.background(Color.Transparent),
                        fontFamily = FontFamily(poppinsRegular),
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Normal,
                        color = MaterialTheme.colors.secondary,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        LaunchedEffect(countLimit, count) {
            if (countLimit != 0) {
                if (count > countLimit) {
                    count -= countLimit
                }
            }
        }

    }

    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {

        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_PAUSE) {
                viewModel.saveTasbih(
                    Tasbih(
                        id = tasbih.id,
                        title = tasbih.title,
                        color = color,
                        countLimit = countLimit,
                        count = count
                    )
                )
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
}