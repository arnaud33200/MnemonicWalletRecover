package ca.arnaud.mnemonicwalletrecover.model

data class WalletInfoDialogModel(
    val title: String = "",
    val fullPublicKey: String = "",
    val formattedPrivateKey: String = "",
    val copyButton: LoadingButtonModel = LoadingButtonModel(),
    val privateKeyMode: PrivateKeyMode = PrivateKeyMode.Hidden,
)

enum class PrivateKeyMode {
    Hidden, Show
}