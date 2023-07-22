package ca.arnaud.mnemonicwalletrecover.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
data class AppColors(
    val primary: Color,
    val primaryVariant: Color,

    val secondary: Color,
    val onSecondary: Color,

    val background: Color,
    val secondaryBackground: Color,

    val primaryLabel: Color,

    val labelOnPrimary: Color,
)

val DefaultAppColors = AppColors(
    primary = Color.Unspecified,
    primaryVariant = Color.Unspecified,
    secondary = Color.Unspecified,
    onSecondary = Color.Unspecified,
    background = Color.Unspecified,
    secondaryBackground = Color.Unspecified,
    primaryLabel = Color.Unspecified,
    labelOnPrimary = Color.Unspecified,
)

val LightAppColors = AppColors(
    primary = Color(0xFF0D9C62),
    primaryVariant = Color(0xFF077E4E),
    secondary = Color(0xFF633CC0),
    onSecondary = Color(0xFFFFFFFF),
    background = Color(0xFFFFFFFF),
    secondaryBackground = Color(0xFFe0e0e0),
    primaryLabel = Color(0xFF000000),
    labelOnPrimary = Color(0xFFFFFFFF)
)

val DarkAppColors = AppColors(
    primary = Color(0xFF15D587),
    primaryVariant = Color(0xFF0DAC6B),
    secondary = Color(0xFF8346CA),
    onSecondary = Color(0xFFFFFFFF),
    background = Color(0xFF272727),
    secondaryBackground = Color(0xFF0e161e),
    primaryLabel = Color(0xFFFFFFFF),
    labelOnPrimary = Color(0xFF131313)
)