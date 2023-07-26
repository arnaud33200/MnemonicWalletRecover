package ca.arnaud.domain.provider.coroutine

import kotlinx.coroutines.CoroutineDispatcher

interface CoroutineContextProvider {

    val executionDispatcher: CoroutineDispatcher
}
