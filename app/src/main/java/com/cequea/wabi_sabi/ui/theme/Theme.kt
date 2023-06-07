package com.cequea.wabi_sabi.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import com.cequea.wabi_sabi.ui.theme.WabiSabiTheme.colors
import com.google.accompanist.systemuicontroller.rememberSystemUiController



private val LightColorPalette = WabiSabiColors(
    brand = wabiSabiPrincipal,
    brandSecondary = wabiSabiSecondary,
    uiBackground = Neutral0,
    uiBorder = wabiSabiSecondary3,
    uiFloated = wabiSabiSecondary2,
    textSecondary = Neutral7,
    textHelp = Neutral6,
    textInteractive = Neutral0,
    textLink = wabiSabiSecondary,
    iconSecondary = Neutral7,
    iconInteractive = Neutral0,
    iconInteractiveInactive = Neutral1,
    error = wabiSabiPrincipal3,
    gradient6_1 = listOf(darkerWabiSabi, darkWabiSabi, originalWabiSabi, lightWabiSabi, lighterWabiSabi),
    gradient6_2 = listOf(lighterWabiSabi2, lightWabiSabi2, originalWabiSabi2, darkWabiSabi2, darkerWabiSabi2),
    gradient3_1 = listOf(lighterWabiSabiSecondary, originalWabiSabiSecondary, darkerWabiSabiSecondary),
    gradient3_2 = listOf(lighterWabiSabiSecondary3, originalWabiSabiSecondary3, darkerWabiSabiSecondary3),
    gradient2_1 = listOf(lightWabiSabiSecondary3, darkWabiSabiSecondary3),
    gradient2_2 = listOf(lighterWabiSabi, darkerWabiSabi),
    gradient2_3 = listOf(lighterWabiSabi2, darkerWabiSabi2),
    tornado1 = listOf(lighterWabiSabiSecondary, darkerWabiSabiSecondary),
    isDark = false
)

private val DarkColorPalette = WabiSabiColors(
    brand = Shadow1,
    brandSecondary = Ocean2,
    uiBackground = Neutral8,
    uiBorder = Neutral3,
    uiFloated = FunctionalDarkGrey,
    textPrimary = Shadow1,
    textSecondary = Neutral0,
    textHelp = Neutral1,
    textInteractive = Neutral7,
    textLink = Ocean2,
    iconPrimary = Shadow1,
    iconSecondary = Neutral0,
    iconInteractive = Neutral7,
    iconInteractiveInactive = Neutral6,
    error = FunctionalRedDark,
    gradient6_1 = listOf(Shadow5, Ocean7, Shadow9, Ocean7, Shadow5),
    gradient6_2 = listOf(Rose11, Lavender7, Rose8, Lavender7, Rose11),
    gradient3_1 = listOf(Shadow9, Ocean7, Shadow5),
    gradient3_2 = listOf(Rose8, Lavender7, Rose11),
    gradient2_1 = listOf(Ocean3, Shadow3),
    gradient2_2 = listOf(Ocean4, Shadow2),
    gradient2_3 = listOf(Lavender3, Rose3),
    tornado1 = listOf(Shadow4, Ocean3),
    isDark = true
)

@Composable
fun WabiSabiTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColorPalette else LightColorPalette

    val sysUiController = rememberSystemUiController()
    SideEffect {
        sysUiController.setSystemBarsColor(
            color = colors.uiBackground.copy(alpha = AlphaNearOpaque)
        )
    }

    ProvideWabiSabiColors(colors) {
        MaterialTheme(
            colorScheme = if (darkTheme) darkColorScheme() else lightColorScheme(),
            typography = Typography,
            shapes = Shapes,
            content = content
        )
    }
}

object WabiSabiTheme {
    val colors: WabiSabiColors
        @Composable
        get() = LocalWabiSabiColors.current
}

/**
 * WabiSabi custom Color Palette
 */
@Stable
class WabiSabiColors(
    gradient6_1: List<Color>,
    gradient6_2: List<Color>,
    gradient3_1: List<Color>,
    gradient3_2: List<Color>,
    gradient2_1: List<Color>,
    gradient2_2: List<Color>,
    gradient2_3: List<Color>,
    brand: Color,
    brandSecondary: Color,
    uiBackground: Color,
    uiBorder: Color,
    uiFloated: Color,
    interactivePrimary: List<Color> = gradient2_1,
    interactiveSecondary: List<Color> = gradient2_2,
    interactiveMask: List<Color> = gradient6_1,
    textPrimary: Color = brand,
    textSecondary: Color,
    textHelp: Color,
    textInteractive: Color,
    textLink: Color,
    tornado1: List<Color>,
    iconPrimary: Color = brand,
    iconSecondary: Color,
    iconInteractive: Color,
    iconInteractiveInactive: Color,
    error: Color,
    notificationBadge: Color = error,
    isDark: Boolean
) {
    var gradient6_1 by mutableStateOf(gradient6_1)
        private set
    var gradient6_2 by mutableStateOf(gradient6_2)
        private set
    var gradient3_1 by mutableStateOf(gradient3_1)
        private set
    var gradient3_2 by mutableStateOf(gradient3_2)
        private set
    var gradient2_1 by mutableStateOf(gradient2_1)
        private set
    var gradient2_2 by mutableStateOf(gradient2_2)
        private set
    var gradient2_3 by mutableStateOf(gradient2_3)
        private set
    var brand by mutableStateOf(brand)
        private set
    var brandSecondary by mutableStateOf(brandSecondary)
        private set
    var uiBackground by mutableStateOf(uiBackground)
        private set
    var uiBorder by mutableStateOf(uiBorder)
        private set
    var uiFloated by mutableStateOf(uiFloated)
        private set
    var interactivePrimary by mutableStateOf(interactivePrimary)
        private set
    var interactiveSecondary by mutableStateOf(interactiveSecondary)
        private set
    var interactiveMask by mutableStateOf(interactiveMask)
        private set
    var textPrimary by mutableStateOf(textPrimary)
        private set
    var textSecondary by mutableStateOf(textSecondary)
        private set
    var textHelp by mutableStateOf(textHelp)
        private set
    var textInteractive by mutableStateOf(textInteractive)
        private set
    var tornado1 by mutableStateOf(tornado1)
        private set
    var textLink by mutableStateOf(textLink)
        private set
    var iconPrimary by mutableStateOf(iconPrimary)
        private set
    var iconSecondary by mutableStateOf(iconSecondary)
        private set
    var iconInteractive by mutableStateOf(iconInteractive)
        private set
    var iconInteractiveInactive by mutableStateOf(iconInteractiveInactive)
        private set
    var error by mutableStateOf(error)
        private set
    var notificationBadge by mutableStateOf(notificationBadge)
        private set
    var isDark by mutableStateOf(isDark)
        private set

    fun update(other: WabiSabiColors) {
        gradient6_1 = other.gradient6_1
        gradient6_2 = other.gradient6_2
        gradient3_1 = other.gradient3_1
        gradient3_2 = other.gradient3_2
        gradient2_1 = other.gradient2_1
        gradient2_2 = other.gradient2_2
        gradient2_3 = other.gradient2_3
        brand = other.brand
        brandSecondary = other.brandSecondary
        uiBackground = other.uiBackground
        uiBorder = other.uiBorder
        uiFloated = other.uiFloated
        interactivePrimary = other.interactivePrimary
        interactiveSecondary = other.interactiveSecondary
        interactiveMask = other.interactiveMask
        textPrimary = other.textPrimary
        textSecondary = other.textSecondary
        textHelp = other.textHelp
        textInteractive = other.textInteractive
        textLink = other.textLink
        tornado1 = other.tornado1
        iconPrimary = other.iconPrimary
        iconSecondary = other.iconSecondary
        iconInteractive = other.iconInteractive
        iconInteractiveInactive = other.iconInteractiveInactive
        error = other.error
        notificationBadge = other.notificationBadge
        isDark = other.isDark
    }

    fun copy(): WabiSabiColors = WabiSabiColors(
        gradient6_1 = gradient6_1,
        gradient6_2 = gradient6_2,
        gradient3_1 = gradient3_1,
        gradient3_2 = gradient3_2,
        gradient2_1 = gradient2_1,
        gradient2_2 = gradient2_2,
        gradient2_3 = gradient2_3,
        brand = brand,
        brandSecondary = brandSecondary,
        uiBackground = uiBackground,
        uiBorder = uiBorder,
        uiFloated = uiFloated,
        interactivePrimary = interactivePrimary,
        interactiveSecondary = interactiveSecondary,
        interactiveMask = interactiveMask,
        textPrimary = textPrimary,
        textSecondary = textSecondary,
        textHelp = textHelp,
        textInteractive = textInteractive,
        textLink = textLink,
        tornado1 = tornado1,
        iconPrimary = iconPrimary,
        iconSecondary = iconSecondary,
        iconInteractive = iconInteractive,
        iconInteractiveInactive = iconInteractiveInactive,
        error = error,
        notificationBadge = notificationBadge,
        isDark = isDark,
    )
}

@Composable
fun ProvideWabiSabiColors(
    colors: WabiSabiColors,
    content: @Composable () -> Unit
) {
    val colorPalette = remember {
        // Explicitly creating a new object here so we don't mutate the initial [colors]
        // provided, and overwrite the values set in it.
        colors.copy()
    }
    colorPalette.update(colors)
    CompositionLocalProvider(LocalWabiSabiColors provides colorPalette, content = content)
}

private val LocalWabiSabiColors = staticCompositionLocalOf<WabiSabiColors> {
    error("No WabiSabiColorPalette provided")
}

/**
 * A Material [Colors] implementation which sets all colors to [debugColor] to discourage usage of
 * [MaterialTheme.colors] in preference to [WabiSabiTheme.colors].
 */
fun lightColorScheme(
    debugColor: Color = Color.Magenta
) = ColorScheme(
    primary = wabiSabiPrincipal,
    onPrimary = wabiSabiPrincipal2,
    primaryContainer = Neutral0,
    onPrimaryContainer = Neutral4,
    inversePrimary = wabiSabiPrincipal3,
    secondary = wabiSabiSecondary,
    onSecondary = wabiSabiSecondary2,
    secondaryContainer = Neutral0,
    onSecondaryContainer = Neutral4,
    tertiary = wabiSabiSecondary3,
    onTertiary = wabiSabiSecondary3,
    tertiaryContainer = wabiSabiPrincipal,
    onTertiaryContainer = wabiSabiPrincipal,
    background = Neutral0,
    onBackground = Neutral8,
    surface = Neutral1,
    onSurface = Neutral7,
    surfaceVariant = Neutral4,
    onSurfaceVariant = Neutral2,
    surfaceTint = wabiSabiSecondary3,
    inverseSurface = wabiSabiSecondary,
    inverseOnSurface = wabiSabiSecondary2,
    error = wabiSabiPrincipal3,
    onError = Rose6,
    errorContainer = wabiSabiSecondary,
    onErrorContainer = wabiSabiSecondary,
    outline = wabiSabiPrincipal3,
    outlineVariant = wabiSabiPrincipal3,
    scrim = wabiSabiPrincipal3
)

fun darkColorScheme(
    debugColor: Color = Color.Magenta
) = ColorScheme(
    primary = Shadow5,
    onPrimary = Ocean3,
    primaryContainer = Neutral0,
    onPrimaryContainer = Neutral4,
    inversePrimary = FunctionalGrey,
    secondary = Ocean8,
    onSecondary = Shadow5,
    secondaryContainer = Neutral0,
    onSecondaryContainer = Neutral4,
    tertiary = debugColor,
    onTertiary = debugColor,
    tertiaryContainer = debugColor,
    onTertiaryContainer = debugColor,
    background = Neutral7,
    onBackground = Neutral0,
    surface = Neutral4,
    onSurface = Neutral1,
    surfaceVariant = Neutral2,
    onSurfaceVariant = Neutral4,
    surfaceTint = Lavender9,
    inverseSurface = Ocean5,
    inverseOnSurface = Ocean6,
    error = red,
    onError = Rose6,
    errorContainer = red,
    onErrorContainer = red,
    outline = red,
    outlineVariant = red,
    scrim = red
)