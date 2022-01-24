package ca.arnaud.mnemonicwalletrecover.model

data class WalletInfoDialogModel(
    val title: String,
    val fullPublicKey: String,
    val formattedPrivateKey: String,
    val copyButton: String
)