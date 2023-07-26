package ca.arnaud.mnemonicwalletrecover.view

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import ca.arnaud.mnemonicwalletrecover.model.TextFieldModel
import ca.arnaud.mnemonicwalletrecover.screen.MainScreenSettings

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ColumnScope.WordFieldGrid(
    rowModifier: Modifier = Modifier,
    onValueChanged: (Int, String) -> Unit,
    onDoneClick: () -> Unit,
    wordFields: () -> List<TextFieldModel>,
    wordValues: (Int) -> String,
    nextFocusIndex: () -> Int?,
    keyboardController: SoftwareKeyboardController?
) {
    val focusRequesters = remember { List(wordFields().size) { FocusRequester() } }
    val columnCount = remember { MainScreenSettings.WALLET_WORDS_COLUMNS }
    val rowCount = remember { wordFields().size / columnCount }
    val nextFocusRequester = nextFocusIndex()?.let { focusRequesters.getOrNull(it) }

    for (y in 0 until rowCount) {
        Row(
            modifier = rowModifier
        ) {
            for (x in 0 until columnCount) {
                val index = remember { (y * columnCount) + x }

                val focusRequester by rememberUpdatedState(focusRequesters[index])

                WordFieldGridItem(
                    modifier = Modifier
                        .focusRequester(focusRequester)
                        .weight(1f)
                        .padding(start = if (x > 0) 3.dp else 0.dp),
                    model = wordFields()[index],
                    wordValue = wordValues(index),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = if (nextFocusRequester != null) ImeAction.Next else ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            nextFocusRequester?.requestFocus()
                        },
                        onDone = {
                            keyboardController?.hide()
                            onDoneClick()
                        }
                    ),
                    onValueChange = { value ->
                        onValueChanged(index, value)
                    },
                )
            }
        }
    }
}