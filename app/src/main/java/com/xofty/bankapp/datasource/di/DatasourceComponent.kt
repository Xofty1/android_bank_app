package com.tictactoe.datasource.di

import com.xofty.bankapp.CardApplication
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DatabaseModule::class, NetworkServiceModule::class])
interface DatasourceComponent {
    fun inject(app: CardApplication)
}