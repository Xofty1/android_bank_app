package com.xofty.bankapp.datasource.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "card_number")
data class CardNumberEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val length: Int,
    val luhn: Boolean
)