package ca.arnaud.mnemonicwalletrecover.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class AppDimensions(
    val buttonHeight: Dp
)

val DefaultAppDimensions = AppDimensions(
    buttonHeight = 50.dp
)