package ca.arnaud.domain.executor

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class JobExecutorProviderImpl @Inject constructor() : JobExecutorProvider {
    override val executionDispatcher: CoroutineDispatcher
        get() = Dispatchers.IO
    override val observerDispatcher: CoroutineDispatcher
        get() = Dispatchers.Main
}
