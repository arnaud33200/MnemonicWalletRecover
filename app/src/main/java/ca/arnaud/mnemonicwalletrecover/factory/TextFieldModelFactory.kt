package ca.arnaud.mnemonicwalletrecover.factory

import ca.arnaud.mnemonicwalletrecover.model.TextFieldModel
import javax.inject.Inject

class TextFieldModelFactory @Inject constructor() {

    fun create(
        value: String,
        enable: Boolean = true,
        changeCallback: (String) -> Unit = {}
    ) = TextFieldModel(
        value = value,
        enabled = enable,
        changeCallback = changeCallback
    )
}