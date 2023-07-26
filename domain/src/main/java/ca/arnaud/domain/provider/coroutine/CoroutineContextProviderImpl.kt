package ca.arnaud.domain.provider.coroutine

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class CoroutineContextProviderImpl @Inject constructor() : CoroutineContextProvider {

    override val executionDispatcher: CoroutineDispatcher = Dispatchers.IO
}
