package ca.arnaud.domain.executor

import kotlinx.coroutines.CoroutineDispatcher

interface JobExecutorProvider {
    val executionDispatcher: CoroutineDispatcher
    val observerDispatcher: CoroutineDispatcher
}
