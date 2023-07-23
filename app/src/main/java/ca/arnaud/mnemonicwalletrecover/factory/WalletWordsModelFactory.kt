package ca.arnaud.mnemonicwalletrecover.factory

import ca.arnaud.mnemonicwalletrecover.model.WalletWordsModel
import javax.inject.Inject

class WalletWordsModelFactory @Inject constructor(
    private val textFieldModelFactory: TextFieldModelFactory
) {

    fun create(
        words: List<String>,
    ): WalletWordsModel {
        return WalletWordsModel(
            words.mapIndexed { index, word ->
                textFieldModelFactory.create(
                    value = word,
                    enable = true
                )
            }
        )
    }

    fun createDisabled(words: List<String>): WalletWordsModel {
        return WalletWordsModel(
            words.map { word -> textFieldModelFactory.create(word, false) }
        )
    }
}