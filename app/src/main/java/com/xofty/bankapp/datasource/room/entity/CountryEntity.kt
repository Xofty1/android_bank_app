package com.xofty.bankapp.datasource.room.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(
    tableName = "country",
    indices = [Index(value = ["numeric"], unique = true)] // Уникальный индекс для поля "numeric"
)
data class CountryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val numeric: String?,
    val alpha2: String?,
    val name: String?,
    val emoji: String?,
    val currency: String?,
    val latitude: Int,
    val longitude: Int
)

