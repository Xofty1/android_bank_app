package com.xofty.bankapp.datasource.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bank")
data class BankEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String?,
    val url: String?,
    val phone: String?,
    val city: String?
)