package ca.arnaud.mnemonicwalletrecover.data

import android.content.ClipData
import android.content.ClipboardManager
import ca.arnaud.domain.model.ClipboardData
import ca.arnaud.domain.repository.ClipboardRepository
import javax.inject.Inject

// TODO - would be better to separate into datasource and service
class ClipboardRepositoryImpl @Inject constructor(
    private val clipboardManager: ClipboardManager,
) : ClipboardRepository {

    override suspend fun setData(clipboardData: ClipboardData) {
        val clipData = when (clipboardData) {
            is ClipboardData.Text -> ClipData.newPlainText(
                clipboardData.text, // label
                clipboardData.text, // text
            )
        }
        clipboardManager.setPrimaryClip(clipData)
    }
}