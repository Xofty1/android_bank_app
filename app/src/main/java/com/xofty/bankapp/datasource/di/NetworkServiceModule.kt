package com.tictactoe.datasource.di

import com.xofty.bankapp.datasource.retrofit.NetworkService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkServiceModule {

    @Provides
    @Singleton
    fun provideNetworkService(): NetworkService {
        return NetworkService()
    }
}