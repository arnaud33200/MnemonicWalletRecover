package ca.arnaud.mnemonicwalletrecover.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.Surface
import androidx.compose.runtime.collectAsState
import ca.arnaud.mnemonicwalletrecover.screen.MainScreen
import ca.arnaud.mnemonicwalletrecover.theme.MnemonicWalletRecoverAppTheme
import ca.arnaud.mnemonicwalletrecover.theme.MnemonicWalletRecoverTheme
import ca.arnaud.mnemonicwalletrecover.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MnemonicWalletRecoverAppTheme {

                Surface(color = MnemonicWalletRecoverTheme.colors.background) {
                    val screenModel = viewModel.screenModel.collectAsState().value
                    val walletWordsModel = viewModel.walletWordsModel.collectAsState().value
                    val wordValues = viewModel.wordValues.collectAsState().value
                    val button = viewModel.button.collectAsState().value
                    val dialog = viewModel.dialog.collectAsState().value

                    MainScreen(
                        model = screenModel,
                        walletWordsModel = { walletWordsModel },
                        wordValues = { index -> wordValues.getOrNull(index) ?: "" },
                        button = button,
                        dialog = dialog,
                        callback = viewModel
                    )
                }
            }
        }
    }
}