package com.xofty.bankapp.datasource.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.xofty.bankapp.datasource.room.entity.CardNumberEntity

@Dao
interface CardNumberDao {
    @Insert
    suspend fun insert(cardNumber: CardNumberEntity): Long

    @Query("SELECT * FROM card_number")
    suspend fun getAll(): List<CardNumberEntity>

    @Query("SELECT * FROM card_number WHERE id = :id")
    suspend fun getById(id: Long): CardNumberEntity?

    @Query("SELECT * FROM card_number WHERE length = :length AND luhn = :luhn LIMIT 1")
    suspend fun getByLengthAndLuhn(length: Int?, luhn: Boolean?): CardNumberEntity?
}