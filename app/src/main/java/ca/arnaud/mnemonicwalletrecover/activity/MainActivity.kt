package ca.arnaud.mnemonicwalletrecover.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import ca.arnaud.mnemonicwalletrecover.screen.MainScreen
import ca.arnaud.mnemonicwalletrecover.theme.MnemonicWalletRecoverAppTheme
import ca.arnaud.mnemonicwalletrecover.theme.MnemonicWalletRecoverTheme
import ca.arnaud.mnemonicwalletrecover.view.WalletInfoDialog
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
                    val screenModel by viewModel.screenModel.collectAsState()
                    val wordValues by viewModel.wordValues.collectAsState()
                    val nextFocusIndex by viewModel.nextEmptyFieldIndex.collectAsState()
                    val button by viewModel.button.collectAsState()
                    val dialog by viewModel.dialog.collectAsState()

                    MainScreen(
                        model = screenModel,
                        wordValues = { index -> wordValues.getOrNull(index) ?: "" },
                        button = { button },
                        nextFocusIndex = { nextFocusIndex },
                        callback = viewModel
                    )

                    dialog?.let { dialogModel ->
                        WalletInfoDialog(
                            model = dialogModel,
                            onDismiss = viewModel::dismissWalletInfoDialogClick,
                            onCopyPrivateKeyClick = viewModel::copyPrivateKeyClick,
                        )
                    }
                }
            }
        }
    }
}