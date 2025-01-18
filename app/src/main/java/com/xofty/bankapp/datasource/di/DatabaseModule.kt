package com.tictactoe.datasource.di

import android.content.Context
import androidx.room.Room
import com.xofty.bankapp.datasource.room.CardInfoDatabase
import com.xofty.bankapp.datasource.room.dao.BankDao
import com.xofty.bankapp.datasource.room.dao.CardInfoDao
import com.xofty.bankapp.datasource.room.dao.CardNumberDao
import com.xofty.bankapp.datasource.room.dao.CountryDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): CardInfoDatabase {
        return Room.databaseBuilder(
            context,
            CardInfoDatabase::class.java,
            "cardInfo_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideGameDao(db: CardInfoDatabase): BankDao = db.bankDao()

    @Provides
    @Singleton
    fun provideUserDao(db: CardInfoDatabase): CountryDao = db.countryDao()

    @Provides
    @Singleton
    fun provideCurrentUserDao(db: CardInfoDatabase): CardNumberDao = db.cardNumberDao()

    @Provides
    @Singleton
    fun provideCardInfoDao(db: CardInfoDatabase): CardInfoDao = db.cardInfoDao()
}
