package ca.arnaud.mnemonicwalletrecover.factory

import ca.arnaud.mnemonicwalletrecover.model.TextFieldModel
import ca.arnaud.mnemonicwalletrecover.model.WalletWordsModel
import javax.inject.Inject

// TODO - merge with screen model factory
class WalletWordsModelFactory @Inject constructor(
) {

    fun create(
        words: List<String>,
    ): WalletWordsModel {
        return WalletWordsModel(
            words.mapIndexed { index, word ->
                TextFieldModel(
                    label = index.toString(),
                    enabled = true
                )
            }
        )
    }

    fun createDisabled(words: List<String>): WalletWordsModel {
        return WalletWordsModel(
            words.mapIndexed { index, word ->
                TextFieldModel(
                    label = index.toString(),
                    enabled = false,
                )
            }
        )
    }
}