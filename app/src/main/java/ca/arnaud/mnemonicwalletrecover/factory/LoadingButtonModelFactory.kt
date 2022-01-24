package ca.arnaud.mnemonicwalletrecover.factory

import androidx.annotation.StringRes
import ca.arnaud.mnemonicwalletrecover.model.LoadingButtonModel
import ca.arnaud.mnemonicwalletrecover.provider.ResourceProvider
import javax.inject.Inject

class LoadingButtonModelFactory @Inject constructor(
    private val resourceProvider: ResourceProvider
) {

    fun create(
        @StringRes titleRes: Int,
        isLoading: Boolean = false,
    ) = LoadingButtonModel(
        title = resourceProvider.getString(titleRes),
        isLoading = isLoading
    )
}