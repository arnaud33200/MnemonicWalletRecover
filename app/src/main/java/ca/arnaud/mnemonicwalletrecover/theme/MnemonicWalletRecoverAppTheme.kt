package ca.arnaud.mnemonicwalletrecover.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf

@Composable
fun MnemonicWalletRecoverAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        localAppColors provides if (darkTheme) DarkAppColors else LightAppColors,
        localAppTypography provides MnemonicWalletRecoverTypography
    ) {
        MaterialTheme(
//            colors = MnemonicWalletRecoverTheme.colors, // TODO
//            typography = MnemonicWalletRecoverTheme.typography, // TODO
//            shapes = Shapes,
            content = content
        )
    }
}

val localAppColors = staticCompositionLocalOf { AppColors() }
val localAppTypography = staticCompositionLocalOf { AppTypography() }

object MnemonicWalletRecoverTheme {
    val colors: AppColors @Composable get() = localAppColors.current
    val typography: AppTypography @Composable get() = localAppTypography.current
}