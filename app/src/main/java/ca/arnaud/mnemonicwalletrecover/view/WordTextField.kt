package ca.arnaud.mnemonicwalletrecover.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusOrder
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ca.arnaud.mnemonicwalletrecover.model.TextFieldModel
import ca.arnaud.mnemonicwalletrecover.theme.MnemonicWalletRecoverAppTheme
import ca.arnaud.mnemonicwalletrecover.theme.MnemonicWalletRecoverTheme
import ca.arnaud.mnemonicwalletrecover.theme.localAppColors
import ca.arnaud.mnemonicwalletrecover.theme.localAppTypography

@Composable
fun WordTextField(
    modifier: Modifier,
    doneClick: () -> Unit,
    wordField: TextFieldModel,
    number: Int,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    focusRequester: FocusRequester,
    nextFocusRequester: FocusRequester?,
) {
    Box(
        modifier = modifier,
    ) {
        TextField(
            modifier = Modifier
                .padding(top = 8.dp)
                .alpha(if (wordField.enabled) 1f else 0.3f)
                .padding(horizontal = 5.dp)
                .border(
                    1.dp,
                    MnemonicWalletRecoverTheme.colors.primaryLabel,
                    shape = RoundedCornerShape(8.dp)
                )
                .focusRequester(focusRequester)
                .focusOrder { nextFocusRequester?.requestFocus() },
            keyboardOptions = keyboardOptions,
            keyboardActions = KeyboardActions(
                onDone = { doneClick() }
            ),
            textStyle = MnemonicWalletRecoverTheme.typography.body1.copy(
                textAlign = TextAlign.Center,
                color = MnemonicWalletRecoverTheme.colors.primaryLabel
            ),
            singleLine = true,
            enabled = wordField.enabled,
            value = wordField.value,
            onValueChange = wordField.changeCallback,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent
            )
        )

        Text(
            modifier = Modifier
                .align(BiasAlignment(-0f, -1f))
                .background(
                    color = localAppColors.current.secondaryBackground,
                    shape = RoundedCornerShape(4.dp),
                )
                .padding(vertical = 3.dp, horizontal = 6.dp),
            text = "$number",
            style = localAppTypography.current.label,
            color = localAppColors.current.primaryLabel,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun WordTextFieldDefaultPreview() {
    MnemonicWalletRecoverAppTheme {
        WordTextField(
            modifier = Modifier,
            doneClick = {},
            wordField = TextFieldModel("Dolphin"),
            focusRequester = remember { FocusRequester() },
            nextFocusRequester = null,
            number = 666,
        )
    }
}