package ca.arnaud.domain.usecase

import ca.arnaud.domain.provider.coroutine.CoroutineContextProvider
import ca.arnaud.domain.formatter.MnemonicWordFormatter
import ca.arnaud.domain.model.CreateWalletParams
import ca.arnaud.domain.model.Wallet
import org.bitcoinj.crypto.ChildNumber
import org.bitcoinj.crypto.DeterministicKey
import org.bitcoinj.crypto.HDUtils
import org.bitcoinj.wallet.DeterministicKeyChain
import org.bitcoinj.wallet.DeterministicSeed
import org.web3j.crypto.Credentials
import org.web3j.utils.Numeric
import javax.inject.Inject

class CreateCryptoWallet @Inject constructor(
    coroutineContextProvider: CoroutineContextProvider,
    private val mnemonicWordFormatter: MnemonicWordFormatter,
) : SuspendableUseCase<CreateWalletParams, Wallet>(coroutineContextProvider) {

    override suspend fun buildRequest(params: CreateWalletParams): Wallet {
        val words = params.words.map { mnemonicWordFormatter.format(it) }
        val creationTimeSecs = 1409478661L // TODO - does it matter?
        val seed = DeterministicSeed(words, null, params.password, creationTimeSecs)
        val chain = DeterministicKeyChain.builder().seed(seed).build()
        val keyPath: List<ChildNumber> = HDUtils.parsePath("M/44H/60H/0H/0/0")
        val key: DeterministicKey = chain.getKeyByPath(keyPath, true)
        val privateKey = Numeric.toHexStringNoPrefix(key.privKey)

        // Web3
        // TODO - whenever we have time, we should call a repo and remote data source
        val credentials: Credentials = Credentials.create(privateKey)
        val walletId = credentials.address

        return Wallet(
            publicKey = walletId,
            privateKey = privateKey
        )
    }
}