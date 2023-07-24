package ca.arnaud.mnemonicwalletrecover.factory

import ca.arnaud.mnemonicwalletrecover.model.MainScreenModel
import ca.arnaud.mnemonicwalletrecover.model.TextFieldModel
import javax.inject.Inject

class MainScreenModelFactory @Inject constructor() {

    fun create(count: Int): MainScreenModel {
        return MainScreenModel( // TODO - call from factory
            title = "Enter your 12 words", // TODO - put in string
            wordFields = List(count) { index ->
                TextFieldModel(label = "${index + 1}")
            }
        )
    }
}