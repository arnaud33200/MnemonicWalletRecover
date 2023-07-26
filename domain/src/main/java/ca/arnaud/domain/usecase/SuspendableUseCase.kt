package ca.arnaud.domain.usecase

import ca.arnaud.domain.provider.coroutine.CoroutineContextProvider
import kotlinx.coroutines.withContext

abstract class SuspendableUseCase<in Params, out Result>(
    private val coroutineContextProvider: CoroutineContextProvider
) {

    suspend fun execute(params: Params): Result =
        withContext(coroutineContextProvider.executionDispatcher) {
            return@withContext buildRequest(params)
        }

    protected abstract suspend fun buildRequest(params: Params): Result
}