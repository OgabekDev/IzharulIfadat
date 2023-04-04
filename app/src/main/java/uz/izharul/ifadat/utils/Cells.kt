package uz.izharul.ifadat.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uz.izharul.ifadat.R
import uz.izharul.ifadat.model.Dua
import uz.izharul.ifadat.model.DuaList
import uz.izharul.ifadat.model.Tasbih


@Composable
fun HomeItem(
    icon: Painter,
    title: String,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(10.dp),
        modifier = modifier
            .size(100.dp, 100.dp),
        color = MaterialTheme.colors.primary
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = icon,
                contentDescription = "Element Icon",
                modifier = Modifier.size(40.dp, 40.dp)
            )
            Text(
                text = title,
                fontFamily = FontFamily(poppinsRegular),
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                color = MaterialTheme.colors.secondary,
                modifier = Modifier.padding(top = 10.dp)
            )
        }
    }
}

@Composable
fun DuaItem(
    duoItem: DuaList,
    onClick: (Int) -> Unit,
    modifier: Modifier
) {

    Surface(
        shape = RoundedCornerShape(20.dp),
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .aspectRatio(4.7f)
            .clickable {
                onClick(duoItem.id)
            },
        color = MaterialTheme.colors.primary
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent)
                .padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Image(
                painter = painterResource(id = if (isDarkMode()) R.drawable.dua_dark else R.drawable.dua_light),
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp, 40.dp)
                    .padding(end = 10.dp)
            )
            Text(
                text = if(isLanLatin(LocalContext.current)) duoItem.titleUz else duoItem.titleOz,
                color = MaterialTheme.colors.secondary,
                fontFamily = FontFamily(poppinsMedium),
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun TasbihItem(
    tasbih: Tasbih,
    onClick: (Tasbih) -> Unit,
    onDeleteClick: (Tasbih) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(20.dp),
        modifier = modifier
            .padding(horizontal = 10.dp)
            .padding(bottom = 10.dp)
            .clip(RoundedCornerShape(20.dp))
            .fillMaxWidth()
            .clickable {
                onClick(tasbih)
            },
        color = MaterialTheme.colors.primary
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(vertical = 2.5.dp)
        ) {
            Surface(
                shape = CircleShape,
                modifier = Modifier
                    .padding(horizontal = 15.dp)
                    .size(40.dp, 40.dp),
                color = Color(tasbih.color)
            ) {}
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 15.dp)
            ) {
                Column {
                    Text(
                        modifier = Modifier
                            .fillMaxSize(.9f),
                        text = tasbih.title,
                        fontSize = 18.sp,
                        fontFamily = FontFamily(poppinsRegular),
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colors.secondary
                    )
                    Text(
                        text = if (tasbih.countLimit != 0) tasbih.countLimit.toString() + " " + stringResource(R.string.marta) else "∞",
                        fontSize = 16.sp,
                        fontFamily = FontFamily(poppinsRegular),
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colors.secondary
                    )
                }
                Image(
                    painter = painterResource(id = R.drawable.ic_x),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(
                        color = MaterialTheme.colors.secondary
                    ),
                    modifier = Modifier
                        .size(24.dp, 24.dp)
                        .clickable {
                            onDeleteClick(tasbih)
                        }
                )
            }
        }
    }
}

@Composable
fun ChapterItem(
    number: Int,
    onClick: () -> Unit
) {

    Surface(
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .padding(top = if (number == 1) 10.dp else 0.dp)
            .padding(horizontal = 10.dp)
            .padding(bottom = 10.dp)
            .fillMaxWidth()
            .aspectRatio(4.7f)
            .clickable {
                onClick()
            },
        color = MaterialTheme.colors.primary
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent)
                .padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_chapter),
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp, 40.dp)
                    .padding(end = 10.dp)
            )
            Text(
                text = "$number-${stringResource(id = R.string.darslik)}",
                color = MaterialTheme.colors.secondary,
                fontFamily = FontFamily(poppinsMedium),
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun DicsItem(
    number: Int,
    text: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = "$number.   $text",
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            fontFamily = FontFamily(poppinsRegular),
            color = MaterialTheme.colors.secondary
        )
    }
}