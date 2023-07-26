package ca.arnaud.mnemonicwalletrecover.di

import ca.arnaud.domain.repository.ClipboardRepository
import ca.arnaud.mnemonicwalletrecover.data.ClipboardRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    @Singleton
    abstract fun bindClipboardRepository(impl: ClipboardRepositoryImpl): ClipboardRepository
}