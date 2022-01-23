package ca.arnaud.mnemonicwalletrecover.mapper

import javax.inject.Inject

class ListToWalletWordsMapper @Inject constructor(): DataMapper<List<String>, String> {

    override fun mapTo(input: List<String>): String {
        return input.joinToString(separator = " ") {
            it.replace(" ", "").lowercase()
        }
    }
}