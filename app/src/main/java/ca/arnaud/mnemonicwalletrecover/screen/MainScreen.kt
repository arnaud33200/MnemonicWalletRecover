package ca.arnaud.mnemonicwalletrecover.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusOrder
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import ca.arnaud.MnemonicWalletRecover.R
import ca.arnaud.mnemonicwalletrecover.model.*
import ca.arnaud.mnemonicwalletrecover.screen.MainScreenSettings.WALLET_WORDS_COLUMNS
import ca.arnaud.mnemonicwalletrecover.theme.MnemonicWalletRecoverAppTheme
import ca.arnaud.mnemonicwalletrecover.theme.MnemonicWalletRecoverTheme
import ca.arnaud.mnemonicwalletrecover.view.LoadingButton

interface MainScreenActionCallback {
    fun recoverWalletButtonClick()

    fun dismissWalletInfoDialogClick()

    fun copyPrivateKeyClick()
}

object MainScreenSettings {
    const val WALLET_WORDS_COLUMNS = 3
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MainScreen(
    model: MainScreenModel,
    walletWordsModel: WalletWordsModel,
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
        val rowCount = walletWordsModel.wordFields.size / columnCount
        val focusRequesters = List(walletWordsModel.wordFields.size) { FocusRequester() }
        val keyboardController = LocalSoftwareKeyboardController.current

        for (y in 0 until rowCount) {
            Row(
                modifier = Modifier.padding(vertical = 5.dp, horizontal = 5.dp)
            ) {
                for (x in 0 until columnCount) {
                    val index = (y * columnCount) + x
                    val wordField = walletWordsModel.wordFields[index]
                    WordTextField(
                        Modifier.weight(1F), wordField,
                        focusRequesters[index], focusRequesters.getOrNull(index + 1)
                    ) {
                        keyboardController?.hide()
                        callback.recoverWalletButtonClick()
                    }

                }
            }
        }

        LoadingButton(
            modifier = Modifier
                .padding(top = 20.dp)
                .height(50.dp),
            model = button
        ) {
            keyboardController?.hide()
            callback.recoverWalletButtonClick()
        }
    }

    dialog?.let { dialogModel ->
        WalletInfoDialog(model = dialogModel, callback)
    }
}

@Composable
private fun WalletInfoDialog(model: WalletInfoDialogModel, callback: MainScreenActionCallback) {
    Dialog(onDismissRequest = {
        callback.dismissWalletInfoDialogClick()
    }) {
        Card(
            shape = RoundedCornerShape(12.dp),
            backgroundColor = MnemonicWalletRecoverTheme.colors.background,
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                val keyResultModifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp)
                    .background(
                        MnemonicWalletRecoverTheme.colors.secondaryBackground,
                        shape = RoundedCornerShape(4.dp)
                    )
                    .padding(5.dp)

                Text(
                    style = MnemonicWalletRecoverTheme.typography.h1,
                    text = model.title,
                    color = MnemonicWalletRecoverTheme.colors.primaryLabel
                )

                Text(
                    modifier = Modifier.padding(top = 15.dp),
                    style = MnemonicWalletRecoverTheme.typography.h2,
                    text = stringResource(R.string.public_key_title),
                    color = MnemonicWalletRecoverTheme.colors.primaryLabel
                )

                Text(
                    modifier = keyResultModifier,
                    style = MnemonicWalletRecoverTheme.typography.body1,
                    text = model.fullPublicKey,
                    color = MnemonicWalletRecoverTheme.colors.primaryLabel
                )

                Text(
                    modifier = Modifier.padding(top = 15.dp),
                    style = MnemonicWalletRecoverTheme.typography.h2,
                    text = stringResource(R.string.private_key_title),
                    color = MnemonicWalletRecoverTheme.colors.primaryLabel
                )

                Text(
                    modifier = keyResultModifier,
                    style = MnemonicWalletRecoverTheme.typography.body1,
                    text = model.formattedPrivateKey,
                    color = MnemonicWalletRecoverTheme.colors.primaryLabel
                )

                LoadingButton(
                    modifier = Modifier
                        .padding(top = 35.dp)
                        .height(MnemonicWalletRecoverTheme.dimensions.buttonHeight),
                    model = model.copyButton,
                ) { callback.copyPrivateKeyClick() }
            }
        }
    }
}

@Composable
private fun WordTextField(
    modifier: Modifier,
    wordField: TextFieldModel,
    focusRequester: FocusRequester,
    nextFocusRequester: FocusRequester?,
    doneClick: () -> Unit
) {
    TextField(
        modifier = modifier
            .focusRequester(focusRequester)
            .focusOrder { nextFocusRequester?.requestFocus() }
            .alpha(if (wordField.enabled) 1f else 0.3f)
            .padding(horizontal = 5.dp)
            .border(
                1.dp,
                MnemonicWalletRecoverTheme.colors.primaryLabel,
                shape = RoundedCornerShape(8.dp)
            ),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = if (nextFocusRequester != null) ImeAction.Next else ImeAction.Done
        ),
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
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MnemonicWalletRecoverAppTheme {
        MainScreen(
            MainScreenModel(
                title = "Enter your 12 words"
            ),
            WalletWordsModel(
                List(12) { "Word ${it + 1}" }.map { value ->
                    TextFieldModel(value, true) { }
                }
            ),
            LoadingButtonModel("Generate Wallet", false),
            WalletInfoDialogModel(
                "Wallet", "o14n132o123no3", "32981312932",
                LoadingButtonModel("Button", false)
            ),
            object : MainScreenActionCallback {
                override fun recoverWalletButtonClick() {

                }

                override fun dismissWalletInfoDialogClick() {

                }

                override fun copyPrivateKeyClick() {

                }

            }
        )
    }
}