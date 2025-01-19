package com.xofty.bankapp.datasource.room.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "card_number",
    indices = [Index(value = ["length", "luhn"], unique = true)] // Уникальный индекс для полей "length" и "luhn"
)
data class CardNumberEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val length: Int,
    val luhn: Boolean
)