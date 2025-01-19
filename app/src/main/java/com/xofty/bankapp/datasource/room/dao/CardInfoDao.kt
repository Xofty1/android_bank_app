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

    @Query("SELECT * FROM card_info ORDER BY id DESC")
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
        val bankId = bank?.let { bankEntity ->
            val existingBank = databaseService.getBankByName(bankEntity.name)
            existingBank?.id ?: databaseService.insertBank(bankEntity)
        } ?: 0

        // Вставляем CardNumber, если он есть
        val cardNumberId = cardNumber?.let { cardNumberEntity ->
            val existingCardNumber = databaseService.getCardNumberByLengthAndLuhn(
                cardNumberEntity.length,
                cardNumberEntity.luhn
            )
            existingCardNumber?.id ?: databaseService.insertCardNumber(cardNumberEntity)
        } ?: 0

        // Вставляем Country, если он есть
        val countryId = country?.let { countryEntity ->
            val existingCountry = databaseService.getCountryByNumeric(countryEntity.numeric)
            existingCountry?.id ?: databaseService.insertCountry(countryEntity)
        } ?: 0

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