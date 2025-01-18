package com.xofty.bankapp.datasource.room

import androidx.room.Database
import androidx.room.RoomDatabase

import androidx.room.Room
import android.content.Context
import com.xofty.bankapp.datasource.room.dao.BankDao
import com.xofty.bankapp.datasource.room.dao.CardInfoDao
import com.xofty.bankapp.datasource.room.dao.CardNumberDao
import com.xofty.bankapp.datasource.room.dao.CountryDao
import com.xofty.bankapp.datasource.room.entity.BankEntity
import com.xofty.bankapp.datasource.room.entity.CardInfoEntity
import com.xofty.bankapp.datasource.room.entity.CardNumberEntity
import com.xofty.bankapp.datasource.room.entity.CountryEntity

@Database(
    entities = [CardInfoEntity::class, CardNumberEntity::class, CountryEntity::class, BankEntity::class],
    version = 1
)
abstract class CardInfoDatabase : RoomDatabase() {

    abstract fun cardInfoDao(): CardInfoDao
    abstract fun cardNumberDao(): CardNumberDao
    abstract fun countryDao(): CountryDao
    abstract fun bankDao(): BankDao

}