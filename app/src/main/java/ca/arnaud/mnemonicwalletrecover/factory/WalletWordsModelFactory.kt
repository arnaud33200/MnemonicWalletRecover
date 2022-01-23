package ca.arnaud.mnemonicwalletrecover.factory

import ca.arnaud.mnemonicwalletrecover.model.TextFieldModel
import ca.arnaud.mnemonicwalletrecover.model.WalletWordsModel
import javax.inject.Inject

class WalletWordsModelFactory @Inject constructor() {

    fun create(words: List<String>, callback: (String, Int) -> Unit): WalletWordsModel {
        return WalletWordsModel(
            words.mapIndexed { index, word ->
                TextFieldModel(word) { text -> callback(text, index) }
            }
        )
    }
}