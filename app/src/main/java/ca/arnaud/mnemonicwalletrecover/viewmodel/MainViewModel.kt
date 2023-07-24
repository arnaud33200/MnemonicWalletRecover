package ca.arnaud.mnemonicwalletrecover.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ca.arnaud.MnemonicWalletRecover.R
import ca.arnaud.domain.model.CreateWalletParams
import ca.arnaud.domain.usecase.GenerateCryptoWallet
import ca.arnaud.mnemonicwalletrecover.factory.LoadingButtonModelFactory
import ca.arnaud.mnemonicwalletrecover.factory.WalletInfoDialogModelFactory
import ca.arnaud.mnemonicwalletrecover.factory.WalletWordsModelFactory
import ca.arnaud.mnemonicwalletrecover.mapper.ListToWalletWordsMapper
import ca.arnaud.mnemonicwalletrecover.model.LoadingButtonModel
import ca.arnaud.mnemonicwalletrecover.model.MainScreenModel
import ca.arnaud.mnemonicwalletrecover.model.WalletInfoDialogModel
import ca.arnaud.mnemonicwalletrecover.model.WalletWordsModel
import ca.arnaud.mnemonicwalletrecover.screen.MainScreenActionCallback
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val generateCryptoWallet: GenerateCryptoWallet,
    private val walletWordsModelFactory: WalletWordsModelFactory,
    private val loadingButtonModelFactory: LoadingButtonModelFactory,
    private val walletInfoDialogModelFactory: WalletInfoDialogModelFactory,
    private val listToWalletWordsMapper: ListToWalletWordsMapper
) : ViewModel(), MainScreenActionCallback {

    companion object {
        const val DEFAULT_WORDS_COUNT = 12
    }

    val screenModel: StateFlow<MainScreenModel> = MutableStateFlow(
        MainScreenModel( // TODO - call from factory
            title = "Enter your 12 words" // TODO - put in string
        )
    )

    private val _wordValues = MutableStateFlow(List(DEFAULT_WORDS_COUNT) { "" })
    val wordValues: StateFlow<List<String>> = _wordValues

    private val _walletWordsModel = MutableStateFlow(generateWalletWordsModel())
    val walletWordsModel: StateFlow<WalletWordsModel> = _walletWordsModel

    private val _button = MutableStateFlow(getDefaultButton())
    val button: StateFlow<LoadingButtonModel> = _button

    private val _dialog = MutableStateFlow<WalletInfoDialogModel?>(null)
    val dialog: StateFlow<WalletInfoDialogModel?> = _dialog

    private fun generateWalletWordsModel(): WalletWordsModel {
        return walletWordsModelFactory.create(wordValues.value)
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
        _walletWordsModel.value = walletWordsModelFactory.createDisabled(wordValues.value)
    }

    private fun hideLoader() {
        _button.value = getDefaultButton()
        _walletWordsModel.value = generateWalletWordsModel()
    }

    // region Click Action

    override fun recoverWalletButtonClick() {
        showLoader()
        viewModelScope.launch {
            val wallet = generateCryptoWallet.execute(
                CreateWalletParams(
                    words = listToWalletWordsMapper.mapTo(wordValues.value)
                )
            )

            hideLoader()
            _dialog.value = walletInfoDialogModelFactory.create(wallet)
        }
    }

    override fun dismissWalletInfoDialogClick() {
        _dialog.value = null
    }

    override fun copyPrivateKeyClick() {
        // TODO - use case to copy key
    }

    override fun onWordFieldChanged(index: Int, text: String) {
        _wordValues.update { list ->
            List(list.size) { i ->
                when (i) {
                    index -> text
                    else -> list[i]
                }
            }
        }
    }

    // region
}