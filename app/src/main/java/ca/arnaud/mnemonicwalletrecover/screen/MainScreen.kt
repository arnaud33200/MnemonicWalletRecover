package ca.arnaud.mnemonicwalletrecover.screen

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ca.arnaud.mnemonicwalletrecover.activity.MainActivity
import ca.arnaud.mnemonicwalletrecover.model.MainScreenModel
import ca.arnaud.mnemonicwalletrecover.model.TextFieldModel
import ca.arnaud.mnemonicwalletrecover.model.WalletWordsModel
import ca.arnaud.mnemonicwalletrecover.screen.MainScreenSettings.WALLET_WORDS_COLUMNS
import ca.arnaud.mnemonicwalletrecover.theme.MnemonicWalletRecoverAppTheme
import ca.arnaud.mnemonicwalletrecover.theme.MnemonicWalletRecoverTheme

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
    result: String,
    callback: MainScreenActionCallback
) {
    Column(
        modifier = Modifier.fillMaxSize(),
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

        // TODO - put a loader inside
        Button(
            modifier = Modifier
                .padding(top = 20.dp)
                .height(50.dp),
            onClick = { callback.recoverWalletButtonClick() }
        ) {
            Text(
                text = "Get Private Key",
                textAlign = TextAlign.Center,
                style = MnemonicWalletRecoverTheme.typography.button1,
                color = MnemonicWalletRecoverTheme.colors.labelOnPrimary
            )
        }

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
                    TextFieldModel(value) { }
                }
            ),
            "0x32oi12n3o21in31o2i3n12",
            object : MainScreenActionCallback {
                override fun recoverWalletButtonClick() {

                }

            }
        )
    }
}