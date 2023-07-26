package ca.arnaud.mnemonicwalletrecover.factory

import ca.arnaud.MnemonicWalletRecover.R
import ca.arnaud.domain.model.Wallet
import ca.arnaud.mnemonicwalletrecover.model.PrivateKeyMode
import ca.arnaud.mnemonicwalletrecover.model.WalletInfoDialogModel
import ca.arnaud.mnemonicwalletrecover.provider.ResourceProvider
import java.security.PrivateKey
import javax.inject.Inject

class WalletInfoDialogModelFactory @Inject constructor(
    private val resourceProvider: ResourceProvider,
    private val loadingButtonModelFactory: LoadingButtonModelFactory
) {

    companion object {
        const val PRIVATE_KEY_CHARACTER_TO_REVEAL_COUNT = 4
    }

    fun create(
        wallet: Wallet
    ): WalletInfoDialogModel {
        val mode = PrivateKeyMode.Hidden
        val formattedPrivateKey = formatPrivateKey(wallet.privateKey, mode)

        return WalletInfoDialogModel(
            title = resourceProvider.getString(R.string.wallet_info_title),
            fullPublicKey = getKeyFormat(wallet.publicKey),
            formattedPrivateKey = formattedPrivateKey,
            copyButton = loadingButtonModelFactory.create(R.string.copy_private_key_button),
            privateKeyMode = mode,
        )
    }

    fun formatPrivateKey(privateKey: String, mode: PrivateKeyMode): String {
        return getKeyFormat(when (mode) {
            PrivateKeyMode.Hidden -> "${
                privateKey.subSequence(0, PRIVATE_KEY_CHARACTER_TO_REVEAL_COUNT)
            } ... ${
                privateKey.subSequence(
                    privateKey.lastIndex - PRIVATE_KEY_CHARACTER_TO_REVEAL_COUNT,
                    privateKey.lastIndex
                )
            }"
            PrivateKeyMode.Show -> privateKey
        })
    }

    private fun getKeyFormat(key: String): String {
        return if (key.startsWith("0x")) key else "0x$key"
    }
}