package ca.arnaud.mnemonicwalletrecover.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ca.arnaud.mnemonicwalletrecover.model.*
import ca.arnaud.mnemonicwalletrecover.screen.MainScreenSettings.WALLET_WORDS_COLUMNS
import ca.arnaud.mnemonicwalletrecover.theme.MnemonicWalletRecoverAppTheme
import ca.arnaud.mnemonicwalletrecover.theme.MnemonicWalletRecoverTheme
import ca.arnaud.mnemonicwalletrecover.view.LoadingButton
import ca.arnaud.mnemonicwalletrecover.view.WalletInfoDialog
import ca.arnaud.mnemonicwalletrecover.view.WordTextField

interface MainScreenActionCallback {
    fun recoverWalletButtonClick()

    fun dismissWalletInfoDialogClick()

    fun copyPrivateKeyClick()

    fun onWordFieldChanged(index: Int, text: String)
}

object MainScreenSettings {
    const val WALLET_WORDS_COLUMNS = 3
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MainScreen(
    model: MainScreenModel,
    walletWordsModel: () -> WalletWordsModel,
    button: LoadingButtonModel,
    dialog: WalletInfoDialogModel?,
    callback: MainScreenActionCallback
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            modifier = Modifier.padding(top = 20.dp, bottom = 20.dp),
            text = model.title,
            textAlign = TextAlign.Center,
            style = MnemonicWalletRecoverTheme.typography.h1,
            color = MnemonicWalletRecoverTheme.colors.primaryLabel
        )

        val columnCount = WALLET_WORDS_COLUMNS
        val wordFields = walletWordsModel().wordFields
        val rowCount = wordFields.size / columnCount
        val focusRequesters = remember { List(wordFields.size) { FocusRequester() } }
        val keyboardController = LocalSoftwareKeyboardController.current

        for (y in 0 until rowCount) {
            Row(
                modifier = Modifier.padding(vertical = 5.dp, horizontal = 5.dp)
            ) {
                for (x in 0 until columnCount) {
                    val index = (y * columnCount) + x
                    val wordField = wordFields.getOrNull(index) ?: TextFieldModel()
                    val focusRequester = focusRequesters.getOrNull(index)
                    val nextFocusRequesterIndex = wordFields.indexOfFirst { textFieldModel ->
                        textFieldModel.value.isBlank()
                    }
                    val nextFocusRequester = focusRequesters.getOrNull(nextFocusRequesterIndex)

                    WordTextField(
                        modifier = Modifier
                            .weight(1F),
                        wordField = { wordField },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = if (nextFocusRequester != null) ImeAction.Next else ImeAction.Done
                        ),
                        focusRequester = focusRequester ?: FocusRequester(),
                        nextFocusRequester = nextFocusRequester,
                        number = index + 1,
                        onValueChange = { value ->
                            callback.onWordFieldChanged(index, value)
                        },
                        doneClick = {
                            keyboardController?.hide()
                            callback.recoverWalletButtonClick()
                        }
                    )
                }
            }
        }

        LoadingButton(
            modifier = Modifier
                .padding(top = 20.dp)
                .height(50.dp),
            model = button,
            onClick = {
                keyboardController?.hide()
                callback.recoverWalletButtonClick()
            },
        )
    }

    dialog?.let { dialogModel ->
        WalletInfoDialog(
            model = dialogModel,
            onDismiss = callback::dismissWalletInfoDialogClick,
            onCopyPrivateKeyClick = callback::copyPrivateKeyClick,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MnemonicWalletRecoverAppTheme {
        MainScreen(
            MainScreenModel(
                title = "Enter your 12 words"
            ),
            {
                WalletWordsModel(
                    List(12) { "Word ${it + 1}" }.map { value ->
                        TextFieldModel(value, true)
                    }
                )
            },
            LoadingButtonModel("Generate Wallet", false),
            null,
            object : MainScreenActionCallback {
                override fun recoverWalletButtonClick() {}
                override fun dismissWalletInfoDialogClick() {}
                override fun copyPrivateKeyClick() {}
                override fun onWordFieldChanged(index: Int, text: String) {}
            }
        )
    }
}