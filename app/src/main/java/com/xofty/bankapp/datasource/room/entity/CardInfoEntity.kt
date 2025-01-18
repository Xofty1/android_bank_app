package com.xofty.bankapp.datasource.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey

@Entity(
    tableName = "card_info",
    foreignKeys = [
        ForeignKey(
            entity = CardNumberEntity::class,
            parentColumns = ["id"],
            childColumns = ["cardNumberId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = CountryEntity::class,
            parentColumns = ["id"],
            childColumns = ["countryId"]    ,
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = BankEntity::class,
            parentColumns = ["id"],
            childColumns = ["bankId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class CardInfoEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val scheme: String?,
    val type: String?,
    val brand: String?,
    val prepaid: Boolean?,
    val cardNumberId: Long,
    val countryId: Long,
    val bankId: Long
)