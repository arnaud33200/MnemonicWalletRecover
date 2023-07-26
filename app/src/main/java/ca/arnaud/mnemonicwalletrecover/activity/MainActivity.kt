package ca.arnaud.mnemonicwalletrecover.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
                    var snackbarMessage by remember { mutableStateOf<String?>(null) }
                    val event by viewModel.event.collectAsState()
                    event?.let { eventState ->
                        when (eventState) {
                            is MainViewModel.Event.PrivateKeyCopied -> {
                                snackbarMessage = eventState.message
                            }
                        }
                    }

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
                        callback = viewModel,
                        snackbarMessage = snackbarMessage,
                    )

                    dialog?.let { dialogModel ->
                        WalletInfoDialog(
                            model = dialogModel,
                            callback = viewModel,
                        )
                    }
                }
            }
        }
    }
}