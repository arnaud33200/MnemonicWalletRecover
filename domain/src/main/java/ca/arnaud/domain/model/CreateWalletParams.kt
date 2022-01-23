package ca.arnaud.domain.model

data class CreateWalletParams(
    val words: String,
    val password: String = ""
)