package com.xofty.bankapp.datasource.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.xofty.bankapp.datasource.room.entity.CountryEntity

@Dao
interface CountryDao {
    @Insert
    suspend fun insert(country: CountryEntity): Long

    @Query("SELECT * FROM country")
    suspend fun getAll(): List<CountryEntity>

    @Query("SELECT * FROM country WHERE id = :id")
    suspend fun getById(id: Long): CountryEntity?
}