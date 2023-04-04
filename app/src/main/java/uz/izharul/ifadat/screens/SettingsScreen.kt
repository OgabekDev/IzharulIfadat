package uz.izharul.ifadat.screens

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import uz.izharul.ifadat.MainActivity
import uz.izharul.ifadat.R
import uz.izharul.ifadat.utils.*

@Composable
fun SettingsScreen(
    navController: NavHostController
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        SettingsBackground()
        SettingsView(navController)
    }

}

@Composable
fun SettingsView(
    navController: NavHostController
) {

    val context = LocalContext.current

    var isLanguageClicked by remember {
        mutableStateOf(false)
    }

    var selectedLanguage by remember {
        mutableStateOf(SharedDatabase(context).getLanguage() ?: "en")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent)
    ) {
        TopBar(
            title = stringResource(R.string.sozlamalar),
            onBackClick = {
                navController.popBackStack()
            },
            isBack = true,
            isSetting = false
        )

        Box {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .padding(top = 10.dp)
                    .height(60.dp)
                    .clickable(
                        interactionSource = MutableInteractionSource(),
                        indication = null
                    ) {
                        isLanguageClicked = !isLanguageClicked
                    },
                shape = RoundedCornerShape(10.dp),
                color = MaterialTheme.colors.primary
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Transparent)
                            .padding(horizontal = 15.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            modifier = Modifier
                                .background(Color.Transparent),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_language),
                                contentDescription = null,
                                colorFilter = ColorFilter.tint(
                                    color = MaterialTheme.colors.secondary
                                ),
                                modifier = Modifier
                                    .size(24.dp, 24.dp)
                            )
                            Text(
                                text = stringResource(R.string.til),
                                fontWeight = FontWeight.Normal,
                                fontFamily = FontFamily(poppinsRegular),
                                fontSize = 18.sp,
                                color = MaterialTheme.colors.secondary,
                                modifier = Modifier
                                    .padding(10.dp)
                            )
                        }

                        Row(
                            modifier = Modifier
                                .background(Color.Transparent)
                                .size(60.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.End
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_drop_down),
                                contentDescription = null,
                                colorFilter = ColorFilter.tint(
                                    color = MaterialTheme.colors.secondary
                                ),
                                modifier = Modifier
                                    .size(24.dp, 24.dp)
                            )
                        }

                    }
                }

            }

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .padding(top = 80.dp)
                    .height(60.dp),
                shape = RoundedCornerShape(10.dp),
                color = MaterialTheme.colors.primary
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Transparent)
                            .padding(horizontal = 15.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            modifier = Modifier
                                .background(Color.Transparent),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_dark_mode),
                                contentDescription = null,
                                colorFilter = ColorFilter.tint(
                                    color = MaterialTheme.colors.secondary
                                ),
                                modifier = Modifier
                                    .size(24.dp, 24.dp)
                            )
                            Text(
                                text = stringResource(R.string.tungi_rejim),
                                fontWeight = FontWeight.Normal,
                                fontFamily = FontFamily(poppinsRegular),
                                fontSize = 18.sp,
                                color = MaterialTheme.colors.secondary,
                                modifier = Modifier
                                    .padding(10.dp)
                            )
                        }

                        Row(
                            modifier = Modifier
                                .background(Color.Transparent),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.End
                        ) {
                            IosSwitch(
                                value = SharedDatabase(context).getBoolean("isDarkMode")
                            ) {
                                changeMode(context, it)
                                context.startActivity(Intent(context, MainActivity::class.java))
                                (context as MainActivity).finish()
                            }
                        }

                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 70.dp)
                    .padding(end = 15.dp)
                    .background(Color.Transparent), horizontalAlignment = Alignment.End
            ) {
                if (isLanguageClicked) {
                    AppLanguage(selected = selectedLanguage) {
                        isLanguageClicked = false
                        selectedLanguage = it
                        SharedDatabase(context).saveLanguage(selectedLanguage)
                        loadLocale(context)
                    }
                }
            }
        }

    }
}