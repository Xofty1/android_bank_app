package com.xofty.bankapp.datasource.room.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "bank",
    indices = [Index(value = ["name"], unique = true)] // Уникальный индекс для поля "name"
)
data class BankEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String?,
    val url: String?,
    val phone: String?,
    val city: String?
)