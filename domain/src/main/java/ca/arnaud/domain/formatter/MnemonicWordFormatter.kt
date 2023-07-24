package ca.arnaud.domain.formatter

import javax.inject.Inject

class MnemonicWordFormatter @Inject constructor() : DataFormatter<String> {

    override fun format(data: String): String {
        return data.lowercase().replace(" ", "")
    }
}