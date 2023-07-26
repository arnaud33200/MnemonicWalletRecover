package ca.arnaud.domain.usecase

import ca.arnaud.domain.model.ClipboardData
import ca.arnaud.domain.provider.coroutine.CoroutineContextProvider
import ca.arnaud.domain.repository.ClipboardRepository
import javax.inject.Inject

class SetClipboard @Inject constructor(
    coroutineContextProvider: CoroutineContextProvider,
    private val clipboardRepository: ClipboardRepository,
) : SuspendableUseCase<ClipboardData, Unit>(coroutineContextProvider) {

    override suspend fun buildRequest(params: ClipboardData) {
        clipboardRepository.setData(params)
    }
}