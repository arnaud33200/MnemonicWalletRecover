package ca.arnaud.domain.model

data class CreateWalletParams(
    val words: MnemonicList<String>,
    val password: String = ""
)