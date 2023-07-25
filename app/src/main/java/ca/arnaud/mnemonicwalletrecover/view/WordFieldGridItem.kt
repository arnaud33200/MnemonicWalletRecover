package ca.arnaud.mnemonicwalletrecover.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ca.arnaud.mnemonicwalletrecover.model.TextFieldModel
import ca.arnaud.mnemonicwalletrecover.theme.MnemonicWalletRecoverAppTheme
import ca.arnaud.mnemonicwalletrecover.theme.MnemonicWalletRecoverTheme
import ca.arnaud.mnemonicwalletrecover.theme.localAppColors
import ca.arnaud.mnemonicwalletrecover.theme.localAppTypography

sealed interface WordTextFieldTheme {

    val backgroundColor: @Composable () -> Color
    val labelColor: @Composable () -> Color
    val borderWidth: Dp

    object Focus : WordTextFieldTheme {
        override val backgroundColor: @Composable () -> Color = {
            localAppColors.current.primary
        }
        override val labelColor: @Composable () -> Color = {
            localAppColors.current.primaryLabel
        }
        override val borderWidth: Dp = 2.dp
    }

    object UnFocus : WordTextFieldTheme {
        override val backgroundColor: @Composable () -> Color = {
            localAppColors.current.primaryLabel
        }
        override val labelColor: @Composable () -> Color = {
            localAppColors.current.labelOnPrimary
        }
        override val borderWidth: Dp = 1.dp
    }
}

@Composable
fun WordFieldGridItem(
    modifier: Modifier,
    onValueChange: (String) -> Unit,
    model: TextFieldModel,
    wordValue: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
) {
    Box(
        modifier = modifier,
    ) {
        var theme by remember { mutableStateOf<WordTextFieldTheme>(WordTextFieldTheme.UnFocus) }
        val enabled = model.enabled
        WordTextField(
            modifier = Modifier
                .alpha(if (enabled) 1f else 0.3f)
                .padding(top = 8.dp)
                .padding(horizontal = 5.dp)
                .border(
                    width = theme.borderWidth,
                    color = theme.backgroundColor(),
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(top = 3.dp)
                .onFocusChanged { state ->
                    theme = when {
                        state.isFocused -> WordTextFieldTheme.Focus
                        else -> WordTextFieldTheme.UnFocus
                    }
                },
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            enabled = enabled,
            value = wordValue,
            onValueChange = onValueChange,
        )

        LabelText(modifier = Modifier
            .align(BiasAlignment(-0f, -1f))
            .background(
                color = theme.backgroundColor(),
                shape = RoundedCornerShape(4.dp),
            )
            .padding(vertical = 2.dp, horizontal = 6.dp),
            text = model.label,
            textColor = theme.labelColor(),
        )
    }
}

@Composable
private fun BoxScope.LabelText(
    modifier: Modifier,
    text: String,
    textColor: Color,
) {
    Text(
        modifier = modifier,
        text = text,
        style = localAppTypography.current.label,
        color = textColor,
    )
}

@Composable
private fun WordTextField(
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
    enabled: Boolean,
    value: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
) {

    TextField(
        modifier = modifier,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        textStyle = MnemonicWalletRecoverTheme.typography.body1.copy(
            textAlign = TextAlign.Center,
            color = MnemonicWalletRecoverTheme.colors.primaryLabel
        ),
        singleLine = true,
        enabled = enabled,
        value = value,
        onValueChange = onValueChange,
        colors = TextFieldDefaults.textFieldColors(
            cursorColor = MnemonicWalletRecoverTheme.colors.primaryLabel,
            backgroundColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent,
        )
    )
}

@Preview(showBackground = true)
@Composable
fun WordTextFieldDefaultPreview() {
    MnemonicWalletRecoverAppTheme {
        WordFieldGridItem(
            modifier = Modifier,
            onValueChange = { },
            model = TextFieldModel(label = "666"),
            wordValue = "Cheeta",
        )
    }
}