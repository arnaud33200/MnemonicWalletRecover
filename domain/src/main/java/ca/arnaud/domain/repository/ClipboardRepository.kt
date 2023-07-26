package ca.arnaud.domain.repository

import ca.arnaud.domain.model.ClipboardData

interface ClipboardRepository {

    suspend fun setData(clipboardData: ClipboardData)
}