package ca.arnaud.domain.usecase

import ca.arnaud.domain.executor.JobExecutorProvider
import kotlinx.coroutines.withContext

abstract class SuspendableUseCase<in Params, out Result>(
    private val jobExecutorProvider: JobExecutorProvider
) {

    suspend fun execute(params: Params): Result =
        withContext(jobExecutorProvider.executionDispatcher) {
            return@withContext buildRequest(params)
        }

    protected abstract suspend fun buildRequest(params: Params): Result
}