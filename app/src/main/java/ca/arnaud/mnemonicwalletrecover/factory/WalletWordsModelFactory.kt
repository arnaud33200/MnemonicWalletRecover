package ca.arnaud.mnemonicwalletrecover.factory

import ca.arnaud.mnemonicwalletrecover.model.WalletWordsModel
import javax.inject.Inject

class WalletWordsModelFactory @Inject constructor(
    private val textFieldModelFactory: TextFieldModelFactory
) {

    fun create(words: List<String>, callback: (String, Int) -> Unit): WalletWordsModel {
        return WalletWordsModel(
            words.mapIndexed { index, word ->
                textFieldModelFactory.create(word, true) { text -> callback(text, index) }
            }
        )
    }

    fun createDisabled(words: List<String>): WalletWordsModel {
        return WalletWordsModel(
            words.map { word -> textFieldModelFactory.create(word, false) }
        )
    }
}