package ca.arnaud.mnemonicwalletrecover.factory

import ca.arnaud.MnemonicWalletRecover.R
import ca.arnaud.domain.model.Wallet
import ca.arnaud.mnemonicwalletrecover.model.WalletInfoDialogModel
import ca.arnaud.mnemonicwalletrecover.provider.ResourceProvider
import javax.inject.Inject

class WalletInfoDialogModelFactory @Inject constructor(
    private val resourceProvider: ResourceProvider
) {

    companion object {
        const val PRIVATE_KEY_CHARACTER_TO_REVEAL_COUNT = 4
    }

    fun create(
        wallet: Wallet
    ): WalletInfoDialogModel {
        val privateKey = wallet.privateKey
        val formattedPrivateKey = "${
            privateKey.subSequence(0, PRIVATE_KEY_CHARACTER_TO_REVEAL_COUNT)
        } ... ${
            privateKey.subSequence(
                privateKey.lastIndex - PRIVATE_KEY_CHARACTER_TO_REVEAL_COUNT,
                privateKey.lastIndex
            )
        }"

        return WalletInfoDialogModel(
            title = resourceProvider.getString(R.string.wallet_info_title),
            fullPublicKey = getKeyFormat(wallet.publicKey),
            formattedPrivateKey = getKeyFormat(formattedPrivateKey),
            copyButton = resourceProvider.getString(R.string.copy_private_key_button),
        )
    }

    private fun getKeyFormat(key: String) = "0x$key"
}