package ca.arnaud.mnemonicwalletrecover.model

data class TextFieldModel(
    val value: String = "",
    val enabled: Boolean = true,
    val changeCallback: (String) -> Unit = {} // TODO to remove, this is bad practice
)