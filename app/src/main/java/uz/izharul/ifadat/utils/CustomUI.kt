package uz.izharul.ifadat.utils

import android.widget.TextView
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.android.material.textview.MaterialTextView
import kotlinx.coroutines.launch
import uz.izharul.ifadat.R
import uz.izharul.ifadat.model.BottomNavItem
import uz.izharul.ifadat.ui.theme.*

@Composable
fun TopBar(
    title: String,
    onBackClick: () -> Unit = {},
    onSettingClick: () -> Unit = {},
    onDicClick: () -> Unit = {},
    isBack: Boolean = false,
    isSetting: Boolean = true,
    isDic: Boolean = false
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .background(MaterialTheme.colors.primary)
            .padding(horizontal = 20.dp)
            .padding(top = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        if (isBack) {
            Image(
                painter = painterResource(id = R.drawable.ic_back),
                contentDescription = null,
                modifier = Modifier
                    .clickable(
                        interactionSource = MutableInteractionSource(),
                        indication = null
                    ) {
                        onBackClick()
                    }
                    .height(24.dp)
                    .width(24.dp),
                colorFilter = ColorFilter.tint(
                    color = NegativeColor
                )
            )
        } else {
            Row(
                modifier = Modifier
                    .height(24.dp)
                    .width(24.dp)
            ) {}
        }

        Text(
            text = title,
            color = MaterialTheme.colors.secondary,
            fontSize = 20.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = FontFamily(poppinsMedium),
            maxLines = 1,
            modifier = Modifier
                .fillMaxWidth(.8f),
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Ellipsis
        )

        Row(
            modifier = Modifier
                .height(70.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {

            if (isDic) {
                Image(
                    painter = painterResource(id = R.drawable.ic_dic),
                    contentDescription = null,
                    modifier = Modifier
                        .clickable(
                            interactionSource = MutableInteractionSource(),
                            indication = null
                        ) {
                            onDicClick()
                        }
                        .padding(end = 15.dp)
                        .height(20.dp)
                        .width(20.dp),
                    colorFilter = ColorFilter.tint(
                        color = NegativeColor
                    )
                )
            }

            if (isSetting) {
                Image(
                    painter = painterResource(id = R.drawable.ic_settings),
                    contentDescription = null,
                    modifier = Modifier
                        .clickable {
                            onSettingClick()
                        }
                        .height(24.dp)
                        .width(24.dp),
                    colorFilter = ColorFilter.tint(
                        color = NegativeColor
                    )
                )
            }
        }

    }
}

@Composable
fun IosSwitch(
    value: Boolean,
    onValueChanged: (Boolean) -> Unit
) {


    val switchPadding: Dp = 3.dp
    val buttonWidth: Dp = 50.dp
    val buttonHeight: Dp = 30.dp

    val switchSize = 24.dp

    val interactionSource = remember {
        MutableInteractionSource()
    }

    var switchClicked by remember {
        mutableStateOf(value)
    }

    var padding by remember {
        mutableStateOf(0.dp)
    }

    padding = if (switchClicked) buttonWidth - switchSize - switchPadding * 2 else 0.dp

    val animateSize by animateDpAsState(
        targetValue = if (switchClicked) padding else 0.dp,
        tween(
            durationMillis = 700,
            delayMillis = 0,
            easing = LinearOutSlowInEasing
        )
    )

    Box(
        modifier = Modifier
            .width(buttonWidth)
            .height(buttonHeight)
            .clip(CircleShape)
            .background(if (switchClicked) Color(0xFF32D74B) else Color(0x5C787880))
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                switchClicked = !switchClicked
                onValueChanged(switchClicked)
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = switchPadding)
                .padding(horizontal = 0.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(animateSize)
                    .background(Color.Transparent)
            )

            Box(
                modifier = Modifier
                    .size(switchSize)
                    .clip(CircleShape)
                    .background(Color.White)
            )

        }
    }
}

@Composable
fun CreateTasbih(
    onClick: (String) -> Unit
) {

    var name by remember {
        mutableStateOf("")
    }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(75.dp)
            .padding(top = 10.dp)
            .padding(horizontal = 10.dp),
        shape = RoundedCornerShape(20.dp),
        color = MaterialTheme.colors.primary
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextField(
                value = name,
                onValueChange = {
                    name = it
                },
                singleLine = true,
                maxLines = 1,
                placeholder = {
                    Text(
                        text = stringResource(R.string.zikrni_kiriting),
                        fontWeight = FontWeight.Normal,
                        fontFamily = FontFamily(poppinsRegular),
                        fontSize = 16.sp,
                        color = NegativeColor
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    textColor = MaterialTheme.colors.secondary,
                    backgroundColor = MaterialTheme.colors.primary,
                    cursorColor = MaterialTheme.colors.secondary,
                    unfocusedIndicatorColor = MaterialTheme.colors.primary
                ),
                modifier = Modifier
                    .fillMaxWidth(.7f)
            )

            Surface(
                modifier = Modifier
                    .height(45.dp)
                    .padding(end = 10.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .clickable {
                        onClick(name)
                        name = ""
                    },
                shape = RoundedCornerShape(10.dp),
                color = BrandColor
            ) {
                Text(
                    text = stringResource(R.string.kiritish),
                    fontWeight = FontWeight.Normal,
                    fontFamily = FontFamily(poppinsRegular),
                    fontSize = 16.sp,
                    color = MaterialTheme.colors.background,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun BottomNavigationBar(
    list: List<BottomNavItem>,
    navController: NavController,
    modifier: Modifier = Modifier,
    onItemClick: (BottomNavItem) -> Unit
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry?.destination

    val bottomBarDestination = list.any { it.route == currentDestination?.route }

    if (bottomBarDestination) {
        BottomNavigation(
            modifier = modifier,
            backgroundColor = MaterialTheme.colors.primary
        ) {
            list.forEach { item ->
                val selected = item.route == currentDestination?.route
                BottomNavigationItem(
                    selected = selected,
                    onClick = {
                        onItemClick(item)
                    },
                    selectedContentColor = BrandColor,
                    unselectedContentColor = NegativeColor,
                    icon = {
                        Icon(
                            painter = item.icon,
                            contentDescription = "Bottom Navigation icon",
                            modifier = Modifier
                                .size(32.dp)
                        )
                    }
                )
            }
        }
    }
}

@Composable
fun TasbihColors(
    onClick: (Long) -> Unit
) {

    val scale = remember {
        Animatable(initialValue = 1f)
    }

    LaunchedEffect(true) {
        launch {
            scale.animateTo(
                targetValue = 1.05f,
                animationSpec = tween(
                    durationMillis = 0
                )
            )
            scale.animateTo(
                targetValue = 1f,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioLowBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
        }
    }

    Surface(
        modifier = Modifier
            .height(50.dp)
            .scale(scale.value)
            .background(Color.Transparent),
        shape = RoundedCornerShape(30),
        color = MaterialTheme.colors.primary,
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(horizontal = 5.dp)
        ) {
            Surface(
                shape = CircleShape,
                color = OrangeTasbih,
                modifier = Modifier
                    .clip(CircleShape)
                    .size(40.dp, 40.dp)
                    .clickable {
                        onClick(0xFFD69B57)
                    }
            ) {}

            Spacer(modifier = Modifier.width(10.dp))

            Surface(
                shape = CircleShape,
                color = GreenTasbih,
                modifier = Modifier
                    .size(40.dp, 40.dp)
                    .clickable {
                        onClick(0xFF03A400)
                    }
            ) {}

            Spacer(modifier = Modifier.width(10.dp))

            Surface(
                shape = CircleShape,
                color = RedTasbih,
                modifier = Modifier
                    .size(40.dp, 40.dp)
                    .clickable {
                        onClick(0xFFFF3838)
                    }
            ) {}

            Spacer(modifier = Modifier.width(10.dp))

            Surface(
                shape = CircleShape,
                color = YellowTasbih,
                modifier = Modifier
                    .size(40.dp, 40.dp)
                    .clickable {
                        onClick(0xFFEDD500)
                    }
            ) {}
        }
    }

}

@Composable
fun TasbihTypes(
    selected: Int,
    onClick: (Int) -> Unit
) {

    val scale = remember {
        Animatable(initialValue = 1f)
    }

    LaunchedEffect(true) {
        launch {
            scale.animateTo(
                targetValue = 1.05f,
                animationSpec = tween(
                    durationMillis = 0
                )
            )
            scale.animateTo(
                targetValue = 1f,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioLowBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
        }
    }

    Surface(
        modifier = Modifier
            .width(50.dp)
            .scale(scale.value)
            .background(Color.Transparent),
        shape = RoundedCornerShape(50),
        color = MaterialTheme.colors.primary,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(horizontal = 5.dp)
        ) {
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "11",
                fontFamily = FontFamily(poppinsRegular),
                fontSize = 20.sp,
                fontWeight = FontWeight.Normal,
                color = if (selected == 11) BrandColor else MaterialTheme.colors.secondary,
                modifier = Modifier
                    .clickable {
                        onClick(11)
                    }
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "33",
                fontFamily = FontFamily(poppinsRegular),
                fontSize = 20.sp,
                fontWeight = FontWeight.Normal,
                color = if (selected == 33) BrandColor else MaterialTheme.colors.secondary,
                modifier = Modifier
                    .clickable {
                        onClick(33)
                    }
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "99",
                fontFamily = FontFamily(poppinsRegular),
                fontSize = 20.sp,
                fontWeight = FontWeight.Normal,
                color = if (selected == 99) BrandColor else MaterialTheme.colors.secondary,
                modifier = Modifier
                    .clickable {
                        onClick(99)
                    }
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "∞",
                fontFamily = FontFamily(poppinsRegular),
                fontSize = 20.sp,
                fontWeight = FontWeight.Normal,
                color = if (selected == 0) BrandColor else MaterialTheme.colors.secondary,
                modifier = Modifier
                    .clickable {
                        onClick(0)
                    }
            )
            Spacer(modifier = Modifier.height(10.dp))
        }
    }

}

@Composable
fun AppLanguage(
    selected: String,
    onClick: (String) -> Unit
) {

    val scale = remember {
        Animatable(initialValue = 1f)
    }

    LaunchedEffect(true) {
        launch {
            scale.animateTo(
                targetValue = 1.05f,
                animationSpec = tween(
                    durationMillis = 0
                )
            )
            scale.animateTo(
                targetValue = 1f,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioLowBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
        }
    }

    Surface(
        modifier = Modifier
            .width(100.dp)
            .scale(scale.value)
            .background(Color.Transparent),
        shape = RoundedCornerShape(20),
        color = MaterialTheme.colors.primary,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(horizontal = 5.dp)
        ) {
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = stringResource(R.string.krill),
                fontFamily = FontFamily(poppinsRegular),
                fontSize = 20.sp,
                fontWeight = FontWeight.Normal,
                color = if (selected == "en") BrandColor else MaterialTheme.colors.secondary,
                modifier = Modifier
                    .clickable {
                        onClick("en")
                    }
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = stringResource(R.string.lotin),
                fontFamily = FontFamily(poppinsRegular),
                fontSize = 20.sp,
                fontWeight = FontWeight.Normal,
                color = if (selected == "uz") BrandColor else MaterialTheme.colors.secondary,
                modifier = Modifier
                    .clickable {
                        onClick("uz")
                    }
            )
            Spacer(modifier = Modifier.height(10.dp))
        }
    }

}

@Composable
fun DicsSection(
    dics: String
) {

    val scale = remember {
        Animatable(initialValue = 1f)
    }

    LaunchedEffect(true) {
        launch {
            scale.animateTo(
                targetValue = 1.05f,
                animationSpec = tween(
                    durationMillis = 0
                )
            )
            scale.animateTo(
                targetValue = 1f,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioLowBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
        }
    }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(.5f)
            .scale(scale.value)
            .padding(20.dp),
        shape = RoundedCornerShape(20.dp),
        color = NegativeColor
    ) {
        val spannedText = HtmlCompat.fromHtml(dics, 0)
        val textColor = if (isDarkMode()) R.color.white else R.color.black
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(1.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(MaterialTheme.colors.primary)
        ) {
            item {
                AndroidView(
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .padding(top = 20.dp)
                        .fillMaxWidth(),
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

@Composable
fun Loading(
    modifier: Modifier? = null
) {

    val surfaceModifier = if (modifier == null) Modifier.size(100.dp) else Modifier.fillMaxSize()

    Surface(
        modifier = surfaceModifier,
        shape = RoundedCornerShape(20),
        color = Color.Transparent
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(56.dp)
                    .align(Alignment.Center),
                color = BrandColor,
                strokeWidth = 5.dp
            )
        }
    }
}