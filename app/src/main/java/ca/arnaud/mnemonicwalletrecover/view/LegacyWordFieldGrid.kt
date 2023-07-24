package ca.arnaud.mnemonicwalletrecover.view

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
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import ca.arnaud.mnemonicwalletrecover.model.WalletWordsModel
import ca.arnaud.mnemonicwalletrecover.screen.MainScreenSettings

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LegacyWordFieldGrid(
    modifier: Modifier = Modifier,
    walletWordsModel: () -> WalletWordsModel,
    wordValues: (Int) -> String,
    onValueChanged: (Int, String) -> Unit,
    onDoneClick: () -> Unit,
    keyboardController: SoftwareKeyboardController?
) {
    val columnCount = MainScreenSettings.WALLET_WORDS_COLUMNS
    val wordFields = walletWordsModel().wordFields
    val rowCount = remember { wordFields.size / columnCount }
    val focusRequesters = remember { List(wordFields.size) { FocusRequester() } }
    //        val keyboardController = LocalSoftwareKeyboardController.current

    for (y in 0 until rowCount) {
        Row(
            modifier = Modifier
                .padding(vertical = 5.dp, horizontal = 5.dp)
        ) {
            for (x in 0 until columnCount) {
                val index = (y * columnCount) + x
                val focusRequester by rememberUpdatedState(focusRequesters[index])
                val nextFocusRequesterIndex = wordFields.indexOfFirst { textFieldModel ->
                    // TODO
                    true
                }
                val nextFocusRequester = focusRequesters.getOrNull(nextFocusRequesterIndex)

                WordFieldGridItem(
                    modifier = Modifier
//                        .focusRequester(focusRequester)
                        .weight(1f)
                        .padding(vertical = 5.dp),
                    model = wordFields[index],
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