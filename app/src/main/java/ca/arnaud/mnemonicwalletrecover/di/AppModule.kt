package ca.arnaud.mnemonicwalletrecover.di

import ca.arnaud.domain.executor.JobExecutorProvider
import ca.arnaud.domain.executor.JobExecutorProviderImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    abstract fun bindJobExecutorProvider(impl: JobExecutorProviderImpl): JobExecutorProvider
}