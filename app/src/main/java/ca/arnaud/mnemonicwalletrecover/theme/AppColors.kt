package ca.arnaud.mnemonicwalletrecover.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
data class AppColors(
    val primary: Color = Color.Unspecified,
    val primaryVariant: Color = Color.Unspecified,
    val secondary: Color = Color.Unspecified,
    val background: Color = Color.Unspecified,
    val primaryLabel: Color = Color.Unspecified,
)

val LightAppColors = AppColors(
    primary = Color(0xFFBB86FC),
    primaryVariant = Color(0xFF6200EE),
    secondary = Color(0xFF03DAC5),
    background = Color(0xFFFFFFFF),
    primaryLabel = Color(0xFF000000)
)

val DarkAppColors = AppColors(
    primary = Color(0xFFBB86FC),
    primaryVariant = Color(0xFF6200EE),
    secondary = Color(0xFF03DAC5),
    background = Color(0xFF000000),
    primaryLabel = Color(0xFFFFFFFF)
)