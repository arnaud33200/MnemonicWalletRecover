package ca.arnaud.mnemonicwalletrecover.model

import ca.arnaud.domain.model.MnemonicList

data class MainScreenModel(
    val title: String,
    val wordFields: MnemonicList<TextFieldModel> = MnemonicList { TextFieldModel() },
)