package com.xofty.bankapp.datasource.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.xofty.bankapp.datasource.room.DatabaseService
import com.xofty.bankapp.datasource.room.entity.BankEntity
import com.xofty.bankapp.datasource.room.entity.CardInfoEntity
import com.xofty.bankapp.datasource.room.entity.CardNumberEntity
import com.xofty.bankapp.datasource.room.entity.CountryEntity

@Dao
interface CardInfoDao {
    @Insert
    suspend fun insert(cardInfo: CardInfoEntity)

    @Query("SELECT * FROM card_info")
    suspend fun getAll(): List<CardInfoEntity>

    @Transaction
    suspend fun insertCardInfoWithDependencies(
        bank: BankEntity?,
        cardNumber: CardNumberEntity?,
        country: CountryEntity?,
        cardInfo: CardInfoEntity,
        databaseService: DatabaseService
    ) {
        // Вставляем Bank, если он есть
        val bankId = bank?.let { databaseService.insertBank(it) } ?: 0

        // Вставляем CardNumber, если он есть
        val cardNumberId = cardNumber?.let { databaseService.insertCardNumber(it) } ?: 0

        // Вставляем Country, если он есть
        val countryId = country?.let { databaseService.insertCountry(it) } ?: 0

        // Проверяем, что все ID корректны
        if (cardNumberId == 0L || countryId == 0L || bankId == 0L) {
            throw IllegalArgumentException("Ошибка: не удалось вставить связанные данные")
        }

        // Вставляем CardInfo
        val fullCardInfo = cardInfo.copy(
            cardNumberId = cardNumberId,
            countryId = countryId,
            bankId = bankId
        )
        insert(fullCardInfo)
    }
}