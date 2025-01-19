package com.xofty.bankapp.datasource.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.xofty.bankapp.datasource.room.entity.BankEntity

@Dao
interface BankDao {
    @Insert
    suspend fun insert(bank: BankEntity): Long

    @Query("SELECT * FROM bank")
    suspend fun getAll(): List<BankEntity>

    @Query("SELECT * FROM bank WHERE id = :id")
    suspend fun getById(id: Long): BankEntity?

    @Query("SELECT * FROM bank WHERE name = :name LIMIT 1")
    suspend fun getByName(name: String?): BankEntity?
}