package ca.arnaud.domain.model

sealed interface ClipboardData {

    data class Text(
        val text: String,
    ): ClipboardData
}