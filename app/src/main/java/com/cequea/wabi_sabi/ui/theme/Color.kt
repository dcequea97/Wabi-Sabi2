package com.cequea.wabi_sabi.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

val Shadow11 = Color(0xff001787)
val Shadow10 = Color(0xff00119e)
val Shadow9 = Color(0xff0009b3)
val Shadow8 = Color(0xff0200c7)
val Shadow7 = Color(0xff0e00d7)
val Shadow6 = Color(0xff2a13e4)
val Shadow5 = Color(0xff4b30ed)
val Shadow4 = Color(0xff7057f5)
val Shadow3 = Color(0xff9b86fa)
val Shadow2 = Color(0xffc8bbfd)
val Shadow1 = Color(0xffded6fe)
val Shadow0 = Color(0xfff4f2ff)

val Ocean11 = Color(0xff005687)
val Ocean10 = Color(0xff006d9e)
val Ocean9 = Color(0xff0087b3)
val Ocean8 = Color(0xff00a1c7)
val Ocean7 = Color(0xff00b9d7)
val Ocean6 = Color(0xff13d0e4)
val Ocean5 = Color(0xff30e2ed)
val Ocean4 = Color(0xff57eff5)
val Ocean3 = Color(0xff86f7fa)
val Ocean2 = Color(0xffbbfdfd)
val Ocean1 = Color(0xffd6fefe)
val Ocean0 = Color(0xfff2ffff)

val Lavender11 = Color(0xff170085)
val Lavender10 = Color(0xff23009e)
val Lavender9 = Color(0xff3300b3)
val Lavender8 = Color(0xff4400c7)
val Lavender7 = Color(0xff5500d7)
val Lavender6 = Color(0xff6f13e4)
val Lavender5 = Color(0xff8a30ed)
val Lavender4 = Color(0xffa557f5)
val Lavender3 = Color(0xffc186fa)
val Lavender2 = Color(0xffdebbfd)
val Lavender1 = Color(0xffebd6fe)
val Lavender0 = Color(0xfff9f2ff)

val Rose11 = Color(0xff7f0054)
val Rose10 = Color(0xff97005c)
val Rose9 = Color(0xffaf0060)
val Rose8 = Color(0xffc30060)
val Rose7 = Color(0xffd4005d)
val Rose6 = Color(0xffe21365)
val Rose5 = Color(0xffec3074)
val Rose4 = Color(0xfff4568b)
val Rose3 = Color(0xfff985aa)
val Rose2 = Color(0xfffdbbcf)
val Rose1 = Color(0xfffed6e2)
val Rose0 = Color(0xfffff2f6)

val Neutral8 = Color(0xff121212)
val Neutral7 = Color(0xde000000)
val Neutral6 = Color(0x99000000)
val Neutral5 = Color(0x61000000)
val Neutral4 = Color(0x1f000000)
val Neutral3 = Color(0x1fffffff)
val Neutral2 = Color(0x61ffffff)
val Neutral1 = Color(0xbdffffff)
val Neutral0 = Color(0xffffffff)

val FunctionalRed = Color(0xffd00036)
val FunctionalRedDark = Color(0xffea6d7e)
val FunctionalGreen = Color(0xff52c41a)
val StarGreen = Color(0xFF2A7704)
val FunctionalGrey = Color(0xfff6f6f6)
val FunctionalDarkGrey = Color(0xff2e2e2e)

val red = Color(0xFFC20D0D)

const val AlphaNearOpaque = 0.95f



val wabiSabiPrincipal = Color(0xFFF25C05)
val lighterWabiSabi = wabiSabiPrincipal.copy( 0.9f)
val lightWabiSabi = wabiSabiPrincipal.copy(0.8f)
val originalWabiSabi = wabiSabiPrincipal
val darkWabiSabi = wabiSabiPrincipal.copy(0.6f)
val darkerWabiSabi = wabiSabiPrincipal.copy(0.5f)


val wabiSabiPrincipal2 = Color(0xFF57B312)
val lighterWabiSabi2 = wabiSabiPrincipal2.copy(0.9f)
val lightWabiSabi2 = wabiSabiPrincipal2.copy(0.8f)
val originalWabiSabi2 = wabiSabiPrincipal2
val darkWabiSabi2 = wabiSabiPrincipal2.copy(0.6f)
val darkerWabiSabi2 = wabiSabiPrincipal2.copy(0.5f)


val wabiSabiPrincipal3 = Color(0xFFBF1304)
val lighterWabiSabi3 = wabiSabiPrincipal3.copy(0.9f)
val lightWabiSabi3 = wabiSabiPrincipal3.copy(0.8f)
val originalWabiSabi3 = wabiSabiPrincipal3
val darkWabiSabi3 = wabiSabiPrincipal3.copy(0.6f)
val darkerWabiSabi3 = wabiSabiPrincipal3.copy(0.5f)

val wabiSabiSecondary = Color(0xFF00CAF5)
val lighterWabiSabiSecondary = wabiSabiSecondary.copy(0.9f)
val lightWabiSabiSecondary = wabiSabiSecondary.copy(0.8f)
val originalWabiSabiSecondary = wabiSabiSecondary
val darkWabiSabiSecondary = wabiSabiSecondary.copy(0.6f)
val darkerWabiSabiSecondary = wabiSabiSecondary.copy(0.5f)


val wabiSabiSecondary2 = Color(0xFFFFAE00)
val lighterWabiSabiSecondary2 = wabiSabiSecondary2.copy(0.9f)
val lightWabiSabiSecondary2 = wabiSabiSecondary2.copy(0.8f)
val originalWabiSabiSecondary2 = wabiSabiSecondary2
val darkWabiSabiSecondary2 = wabiSabiSecondary2.copy(0.6f)
val darkerWabiSabiSecondary2 = wabiSabiSecondary2.copy(0.5f)


val wabiSabiSecondary3 = Color(0xFF002FB3)
val lighterWabiSabiSecondary3 = wabiSabiSecondary3.copy(0.9f)
val lightWabiSabiSecondary3 = wabiSabiSecondary3.copy(0.8f)
val originalWabiSabiSecondary3 = wabiSabiSecondary3
val darkWabiSabiSecondary3 = wabiSabiSecondary3.copy(0.6f)
val darkerWabiSabiSecondary3 = wabiSabiSecondary3.copy(0.5f)