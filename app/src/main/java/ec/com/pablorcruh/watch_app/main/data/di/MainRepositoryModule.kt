package ec.com.pablorcruh.watch_app.main.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ec.com.pablorcruh.watch_app.main.data.repository.MainRepositoryImpl
import ec.com.pablorcruh.watch_app.main.domain.repository.MainRepository
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class MainRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindMainRepository(
        mainRepositoryImpl: MainRepositoryImpl
    ): MainRepository

}