package ca.arnaud.mnemonicwalletrecover.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ca.arnaud.domain.model.MnemonicList
import ca.arnaud.mnemonicwalletrecover.model.*
import ca.arnaud.mnemonicwalletrecover.screen.MainScreenSettings.WALLET_WORDS_COLUMNS
import ca.arnaud.mnemonicwalletrecover.screen.MainScreenSettings.screenPadding
import ca.arnaud.mnemonicwalletrecover.theme.MnemonicWalletRecoverAppTheme
import ca.arnaud.mnemonicwalletrecover.theme.MnemonicWalletRecoverTheme
import ca.arnaud.mnemonicwalletrecover.theme.localAppColors
import ca.arnaud.mnemonicwalletrecover.view.LegacyWordFieldGrid
import ca.arnaud.mnemonicwalletrecover.view.LoadingButton
import ca.arnaud.mnemonicwalletrecover.view.WordFieldGridItem

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
                    .verticalScroll(scrollState), // Doesn't work with lazyVerticalGrid but just don't need it?
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                if (true) {
                    LegacyWordFieldGrid(
                        modifier = Modifier.padding(vertical = 5.dp, horizontal = 5.dp),
                        wordFields = { model.wordFields },
                        wordValues = wordValues,
                        nextFocusIndex = nextFocusIndex,
                        onValueChanged = callback::onWordFieldChanged,
                        onDoneClick = callback::recoverWalletButtonClick,
                        keyboardController = keyboardController,
                    )
                } else {
                    WordFieldGrid(
                        modifier = Modifier.padding(vertical = 5.dp, horizontal = 5.dp),
                        wordFields = model.wordFields,
                        wordValues = wordValues,
                        onValueChanged = callback::onWordFieldChanged,
                        onDoneClick = callback::recoverWalletButtonClick,
                        keyboardController = keyboardController,
                    )
                }

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

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun WordFieldGrid(
    modifier: Modifier = Modifier,
    wordFields: List<TextFieldModel>,
    wordValues: (Int) -> String,
    onValueChanged: (Int, String) -> Unit,
    onDoneClick: () -> Unit,
    keyboardController: SoftwareKeyboardController?
) {
    val focusRequesters = remember { List(wordFields.size) { FocusRequester() } }
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(WALLET_WORDS_COLUMNS),
    ) {
        itemsIndexed(
            items = wordFields,
            key = { _, item -> item.label },
        ) { index, item ->
            val focusRequester = remember { focusRequesters[index] }
            val nextFocusRequesterIndex =
                index + 1 // TODO - look at the values and put next for first empty vallule
            val nextFocusRequester = focusRequesters.getOrNull(nextFocusRequesterIndex)
            WordFieldGridItem(
                modifier = Modifier
                    .focusRequester(focusRequester)
                    .padding(vertical = 5.dp),
                model = item,
                wordValue = wordValues(index),
                keyboardOptions = KeyboardOptions.Default.copy(
                    // TODO enable when fix
//                    imeAction = if (nextFocusRequester != null) ImeAction.Next else ImeAction.Done
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
//                        nextFocusRequester?.requestFocus()
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