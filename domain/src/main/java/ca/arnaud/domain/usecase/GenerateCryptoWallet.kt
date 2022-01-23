package ca.arnaud.domain.usecase

import ca.arnaud.domain.executor.JobExecutorProvider
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

class GenerateCryptoWallet @Inject constructor(
    jobExecutorProvider: JobExecutorProvider
) : SuspendableUseCase<CreateWalletParams, Wallet>(jobExecutorProvider) {

    override suspend fun buildRequest(params: CreateWalletParams): Wallet {
        val seed = DeterministicSeed(params.words, null, params.password, 1409478661L)
        val chain = DeterministicKeyChain.builder().seed(seed).build()
        val keyPath: List<ChildNumber> = HDUtils.parsePath("M/44H/60H/0H/0/0")
        val key: DeterministicKey = chain.getKeyByPath(keyPath, true)
        val privateKey = Numeric.toHexStringNoPrefix(key.privKey)

        // Web3
        val credentials: Credentials = Credentials.create(privateKey)
        val walletId = credentials.address

        return Wallet(
            publicKey = walletId,
            privateKey = privateKey
        )
    }
}