package ca.arnaud.mnemonicwalletrecover.provider

import android.content.Context
import androidx.annotation.StringRes
import javax.inject.Inject

class ResourceProvider @Inject constructor(
    private val context: Context
) {

    fun getString(@StringRes res: Int): String {
        return context.getString(res)
    }
}