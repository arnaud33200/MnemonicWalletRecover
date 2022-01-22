package ca.arnaud.mnemonicwalletrecover.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@Immutable
data class AppTypography(
    val h1: TextStyle = TextStyle.Default,
    val body1: TextStyle = TextStyle.Default,
    val button1: TextStyle = TextStyle.Default
)

val MnemonicWalletRecoverTypography: AppTypography
    @Composable get() = AppTypography(
        h1 = createTextStyle(
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold
        ),
        body1 = createTextStyle(
            fontSize = 17.sp
        ),
        button1 = createTextStyle(
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
    )

private val defaultTextColor
    @Composable get() = Color.Transparent // force the ui to choose a text color
//    @Composable get() = MnemonicWalletRecoverTheme.colors.primaryLabel // doesn't work 🤔

@Composable
private fun createTextStyle(
    fontSize: TextUnit = 12.sp,
    fontFamily: FontFamily = FontFamily.SansSerif,
    fontWeight: FontWeight = FontWeight.Normal
) = TextStyle(
    fontSize = fontSize,
    fontFamily = fontFamily,
    fontWeight = fontWeight,
    color = defaultTextColor
)