package ca.arnaud.mnemonicwalletrecover.screen

import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ca.arnaud.mnemonicwalletrecover.model.LoadingButtonModel
import ca.arnaud.mnemonicwalletrecover.model.MainScreenModel
import ca.arnaud.mnemonicwalletrecover.model.TextFieldModel
import ca.arnaud.mnemonicwalletrecover.model.WalletWordsModel
import ca.arnaud.mnemonicwalletrecover.screen.MainScreenSettings.WALLET_WORDS_COLUMNS
import ca.arnaud.mnemonicwalletrecover.theme.MnemonicWalletRecoverAppTheme
import ca.arnaud.mnemonicwalletrecover.theme.MnemonicWalletRecoverTheme
import ca.arnaud.mnemonicwalletrecover.view.LoadingButton

interface MainScreenActionCallback {
    fun recoverWalletButtonClick()
}

object MainScreenSettings {
    const val WALLET_WORDS_COLUMNS = 3
}

@Composable
fun MainScreen(
    model: MainScreenModel,
    walletWordsModel: WalletWordsModel,
    button: LoadingButtonModel,
    result: String,
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
        for (y in 0 until rowCount) {
            Row(
                modifier = Modifier.padding(vertical = 5.dp, horizontal = 5.dp)
            ) {
                for (x in 0 until columnCount) {
                    val index = (y * columnCount) + x
                    val wordField = walletWordsModel.wordFields[index]
                    WordTextField(Modifier.weight(1F), wordField)
                }
            }
        }

        LoadingButton(
            modifier = Modifier
                .padding(top = 20.dp)
                .height(50.dp),
            model = button
        ) { callback.recoverWalletButtonClick() }

        Text(
            modifier = Modifier.padding(30.dp),
            text = result,
            style = MnemonicWalletRecoverTheme.typography.body1,
            color = MnemonicWalletRecoverTheme.colors.primaryLabel.copy(alpha = 0.8F)
        )
    }
}

@Composable
fun WordTextField(modifier: Modifier, wordField: TextFieldModel) {
    TextField(
        modifier = modifier
            .alpha(if (wordField.enabled) 1f else 0.3f)
            .padding(horizontal = 5.dp)
            .border(
                1.dp,
                MnemonicWalletRecoverTheme.colors.primaryLabel,
                shape = RoundedCornerShape(8.dp)
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
            "0x32oi12n3o21in31o2i3n12",
            object : MainScreenActionCallback {
                override fun recoverWalletButtonClick() {

                }

            }
        )
    }
}