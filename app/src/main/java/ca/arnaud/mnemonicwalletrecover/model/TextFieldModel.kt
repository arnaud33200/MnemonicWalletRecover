package ca.arnaud.mnemonicwalletrecover.model

data class TextFieldModel(
    val value: String,
    val changeCallback: (String) -> Unit
)