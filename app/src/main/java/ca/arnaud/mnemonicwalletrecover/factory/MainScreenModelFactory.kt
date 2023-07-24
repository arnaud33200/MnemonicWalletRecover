package ca.arnaud.mnemonicwalletrecover.factory

import ca.arnaud.domain.model.MnemonicList
import ca.arnaud.mnemonicwalletrecover.model.MainScreenModel
import ca.arnaud.mnemonicwalletrecover.model.TextFieldModel
import javax.inject.Inject

class MainScreenModelFactory @Inject constructor() {

    fun create(): MainScreenModel {
        return MainScreenModel( // TODO - call from factory
            title = "Enter your 12 words", // TODO - put in string
            wordFields = MnemonicList { index ->
                TextFieldModel(label = "${index + 1}")
            }
        )
    }
}