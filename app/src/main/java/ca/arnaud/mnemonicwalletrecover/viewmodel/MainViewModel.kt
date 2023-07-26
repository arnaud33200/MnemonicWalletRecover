package ca.arnaud.mnemonicwalletrecover.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ca.arnaud.MnemonicWalletRecover.R
import ca.arnaud.domain.formatter.MnemonicWordFormatter
import ca.arnaud.domain.model.ClipboardData
import ca.arnaud.domain.model.CreateWalletParams
import ca.arnaud.domain.model.MnemonicList
import ca.arnaud.domain.model.Wallet
import ca.arnaud.domain.usecase.CreateCryptoWallet
import ca.arnaud.domain.usecase.SetClipboard
import ca.arnaud.mnemonicwalletrecover.factory.LoadingButtonModelFactory
import ca.arnaud.mnemonicwalletrecover.factory.MainScreenModelFactory
import ca.arnaud.mnemonicwalletrecover.factory.WalletInfoDialogModelFactory
import ca.arnaud.mnemonicwalletrecover.model.LoadingButtonModel
import ca.arnaud.mnemonicwalletrecover.model.MainScreenModel
import ca.arnaud.mnemonicwalletrecover.model.WalletInfoDialogModel
import ca.arnaud.mnemonicwalletrecover.screen.MainScreenActionCallback
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val createCryptoWallet: CreateCryptoWallet,
    private val setClipboard: SetClipboard,
    private val mainScreenModelFactory: MainScreenModelFactory,
    private val loadingButtonModelFactory: LoadingButtonModelFactory,
    private val walletInfoDialogModelFactory: WalletInfoDialogModelFactory,
    private val mnemonicWordFormatter: MnemonicWordFormatter,
) : ViewModel(), MainScreenActionCallback {

    private val _screenModel = MutableStateFlow(mainScreenModelFactory.create())
    val screenModel: StateFlow<MainScreenModel> = _screenModel

    private val _wordValues = MutableStateFlow(MnemonicList { "" })
    val wordValues: StateFlow<MnemonicList<String>> = _wordValues

    private val _nextEmptyFieldIndex = MutableStateFlow<Int?>(0)
    val nextEmptyFieldIndex: StateFlow<Int?> = _nextEmptyFieldIndex

    private val _button = MutableStateFlow(getDefaultButton())
    val button: StateFlow<LoadingButtonModel> = _button

    private val _dialog = MutableStateFlow<WalletInfoDialogModel?>(null)
    val dialog: StateFlow<WalletInfoDialogModel?> = _dialog

    private val _event = MutableStateFlow<Event?>(null)
    val event: StateFlow<Event?> = _event

    private var wallet: Wallet? = null

    sealed interface Event {
        data class PrivateKeyCopied(
            val message: String,
        ) : Event
    }

    private fun getDefaultButton(): LoadingButtonModel {
        return loadingButtonModelFactory.create(
            titleRes = R.string.generate_wallet_button
        )
    }

    private fun showLoader() {
        _button.value = loadingButtonModelFactory.create(
            titleRes = R.string.generating_wallet_button,
            isLoading = true
        )
        enableFields(false)
    }

    private fun hideLoader() {
        _button.value = getDefaultButton()
        enableFields(true)
    }

    private fun enableFields(enabled: Boolean) {
        _screenModel.update { model ->
            model.copy(wordFields = model.wordFields.map { field ->
                field.copy(enabled = enabled)
            })
        }
    }

    // region Click Action

    override fun recoverWalletButtonClick() {
        showLoader()
        viewModelScope.launch {
            wallet = createCryptoWallet.execute(
                CreateWalletParams(
                    words = wordValues.value,
                    password = "" // TODO - add field
                )
            )
            hideLoader()

            wallet?.let { createdWallet ->
                // TODO - setup a viewModel event
                _dialog.value = walletInfoDialogModelFactory.create(createdWallet)
            }
        }
    }

    override fun dismissWalletInfoDialogClick() {
        wallet = null
        _dialog.value = null
    }

    override fun copyPrivateKeyClick() {
        wallet?.privateKey?.let { privateKey ->
            viewModelScope.launch {
                setClipboard.execute(ClipboardData.Text(text = privateKey))
            }
            _event.value = Event.PrivateKeyCopied(
                message = "Private key copied!" // TODO - add in string provider
            )
        }
    }

    override fun onWordFieldChanged(index: Int, text: String) {
        _wordValues.update { list ->
            MnemonicList { i ->
                when (i) {
                    index -> mnemonicWordFormatter.format(text)
                    else -> list[i]
                }
            }
        }
        _nextEmptyFieldIndex.value = wordValues.value.indexOfFirst { it.isBlank() }
            .takeIf { it >= 0 }
    }

    // region
}