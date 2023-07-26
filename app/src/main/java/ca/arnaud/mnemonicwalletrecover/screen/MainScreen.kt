package ca.arnaud.mnemonicwalletrecover.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ca.arnaud.domain.model.MnemonicList
import ca.arnaud.mnemonicwalletrecover.model.*
import ca.arnaud.mnemonicwalletrecover.screen.MainScreenSettings.screenPadding
import ca.arnaud.mnemonicwalletrecover.theme.MnemonicWalletRecoverAppTheme
import ca.arnaud.mnemonicwalletrecover.theme.MnemonicWalletRecoverTheme
import ca.arnaud.mnemonicwalletrecover.theme.localAppColors
import ca.arnaud.mnemonicwalletrecover.view.WordFieldGrid
import ca.arnaud.mnemonicwalletrecover.view.LoadingButton

interface MainScreenActionCallback {
    fun recoverWalletButtonClick()

    fun onWordFieldChanged(index: Int, text: String)

    fun onPasswordFieldChanged(text: String)
}

object MainScreenSettings {
    const val WALLET_WORDS_COLUMNS = 3
    val screenPadding = 10.dp
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MainScreen(
    model: MainScreenModel,
    wordValues: (Int) -> String,
    button: () -> LoadingButtonModel,
    nextFocusIndex: () -> Int?,
    snackbarMessage: String?,
    passwordValue: () -> String,
    callback: MainScreenActionCallback
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(snackbarMessage) {
        snackbarMessage?.let { message ->
            snackbarHostState.showSnackbar(
                message = message,
            )
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        backgroundColor = localAppColors.current.background, // TODO - not set after moving theme fully to material
        topBar = {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, bottom = 20.dp),
                text = model.title,
                textAlign = TextAlign.Center,
                style = MnemonicWalletRecoverTheme.typography.h2,
                color = MnemonicWalletRecoverTheme.colors.primaryLabel
            )
        },
        content = { paddingValues ->
            val scrollState = rememberScrollState()
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                WordFieldGrid(
                    rowModifier = Modifier
                        .padding(top = 5.dp)
                        .padding(horizontal = screenPadding),
                    wordFields = { model.wordFields },
                    wordValues = wordValues,
                    nextFocusIndex = nextFocusIndex,
                    onValueChanged = callback::onWordFieldChanged,
                    onDoneClick = callback::recoverWalletButtonClick,
                    keyboardController = keyboardController,
                )

                WalletPasswordField(
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .padding(horizontal = screenPadding)
                        .fillMaxWidth(),
                    value = passwordValue,
                    onValueChanged = callback::onPasswordFieldChanged,
                )
            }
        },
        bottomBar = {
            LoadingButton(
                modifier = Modifier
                    .padding(screenPadding)
                    .height(50.dp)
                    .fillMaxWidth(),
                model = button,
                onClick = {
                    keyboardController?.hide()
                    callback.recoverWalletButtonClick()
                }
            )
        }
    )
}

@Composable
private fun WalletPasswordField(
    modifier: Modifier,
    onValueChanged: (String) -> Unit,
    value: () -> String,
) {
    TextField(
        modifier = modifier,
        value = value(),
        onValueChange = onValueChanged,
        colors = TextFieldDefaults.textFieldColors(
            textColor = MnemonicWalletRecoverTheme.colors.primaryLabel
        ),
        placeholder = {
            Text(
                style = MnemonicWalletRecoverTheme.typography.label,
                text = "Password",
                color = MnemonicWalletRecoverTheme.colors.primaryLabel.copy(alpha = 0.7f),
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MnemonicWalletRecoverAppTheme {
        MainScreen(
            model = MainScreenModel(
                title = "Enter your 12 words",
                wordFields = MnemonicList { "${it + 1}" }.map { label -> TextFieldModel(label) }
            ),
            wordValues = { index -> "Word ${index + 1}" },
            button = { LoadingButtonModel("Generate Wallet", false) },
            nextFocusIndex = { null },
            snackbarMessage = null,
            passwordValue = { "" },
            callback = object : MainScreenActionCallback {
                override fun recoverWalletButtonClick() {}
                override fun onWordFieldChanged(index: Int, text: String) {}
                override fun onPasswordFieldChanged(text: String) {}
            }
        )
    }
}