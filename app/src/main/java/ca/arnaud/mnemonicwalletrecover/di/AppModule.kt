package ca.arnaud.mnemonicwalletrecover.di

import android.content.Context
import ca.arnaud.domain.provider.coroutine.CoroutineContextProvider
import ca.arnaud.domain.provider.coroutine.CoroutineContextProviderImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    companion object {

        @Provides
        fun provideContext(@ApplicationContext appContext: Context): Context {
            return appContext
        }
    }

    @Binds
    abstract fun bindJobExecutorProvider(impl: CoroutineContextProviderImpl): CoroutineContextProvider
}