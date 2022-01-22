package ca.arnaud.mnemonicwalletrecover.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import ca.arnaud.mnemonicwalletrecover.theme.MnemonicWalletRecoverAppTheme
import ca.arnaud.mnemonicwalletrecover.theme.MnemonicWalletRecoverTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MnemonicWalletRecoverAppTheme(
                darkTheme = true
            ) {

                Surface(color = MnemonicWalletRecoverTheme.colors.background) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Mnemonic Wallet Recover",
            textAlign = TextAlign.Center,
            style = MnemonicWalletRecoverTheme.typography.h1,
            color = MnemonicWalletRecoverTheme.colors.primaryLabel
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MnemonicWalletRecoverAppTheme {
        MainScreen()
    }
}