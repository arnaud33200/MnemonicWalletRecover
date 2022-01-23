package ca.arnaud.mnemonicwalletrecover.viewmodel

import androidx.lifecycle.ViewModel
import ca.arnaud.mnemonicwalletrecover.factory.WalletWordsModelFactory
import ca.arnaud.mnemonicwalletrecover.model.MainScreenModel
import ca.arnaud.mnemonicwalletrecover.model.WalletWordsModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val walletWordsModelFactory: WalletWordsModelFactory
) : ViewModel() {

    companion object {
        const val DEFAULT_WORDS_COUNT = 12
    }

    val screenModel: StateFlow<MainScreenModel> = MutableStateFlow(MainScreenModel( // TODO - call from factory
        title = "Enter your 12 words" // TODO - put in string
    ))

    private val words = MutableList(DEFAULT_WORDS_COUNT) { "" }

    private val _walletWordsModel = MutableStateFlow(generateWalletWordsModel())
    val walletWordsModel: StateFlow<WalletWordsModel> = _walletWordsModel

    private fun generateWalletWordsModel(): WalletWordsModel {
        return walletWordsModelFactory.create(words) { text, index ->
            updateWalletWord(text, index)
        }
    }

    private fun updateWalletWord(text: String, index: Int) {
        words[index] = text
        _walletWordsModel.value = generateWalletWordsModel()
    }
}