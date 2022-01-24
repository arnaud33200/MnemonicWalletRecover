package ca.arnaud.mnemonicwalletrecover.model

data class TextFieldModel(
    val value: String,
    val enabled: Boolean,
    val changeCallback: (String) -> Unit
)