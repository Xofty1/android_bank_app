package com.xofty.bankapp.datasource.room

import android.content.Context
import com.xofty.bankapp.datasource.room.dao.BankDao
import com.xofty.bankapp.datasource.room.dao.CardInfoDao
import com.xofty.bankapp.datasource.room.dao.CardNumberDao
import com.xofty.bankapp.datasource.room.dao.CountryDao
import com.xofty.bankapp.datasource.room.entity.BankEntity
import com.xofty.bankapp.datasource.room.entity.CardInfoEntity
import com.xofty.bankapp.datasource.room.entity.CardNumberEntity
import com.xofty.bankapp.datasource.room.entity.CountryEntity
import javax.inject.Inject

class DatabaseService @Inject constructor(db: CardInfoDatabase) {

    // DAO объекты
    val cardInfoDao: CardInfoDao = db.cardInfoDao()
    val cardNumberDao: CardNumberDao = db.cardNumberDao()
    val countryDao: CountryDao = db.countryDao()
    val bankDao: BankDao = db.bankDao()

    // Методы для работы с CardInfo
    suspend fun insertCardInfo(cardInfo: CardInfoEntity) {
        cardInfoDao.insert(cardInfo)
    }

    suspend fun getAllCardInfos(): List<CardInfoEntity> {
        return cardInfoDao.getAll()
    }

    // Методы для работы с CardNumber
    suspend fun insertCardNumber(cardNumber: CardNumberEntity): Long {
        return cardNumberDao.insert(cardNumber)
    }

    suspend fun getAllCardNumbers(): List<CardNumberEntity> {
        return cardNumberDao.getAll()
    }

    // Методы для работы с Country
    suspend fun insertCountry(country: CountryEntity): Long {
        return countryDao.insert(country)
    }

    suspend fun getAllCountries(): List<CountryEntity> {
        return countryDao.getAll()
    }

    // Методы для работы с Bank
    suspend fun insertBank(bank: BankEntity): Long {
        return bankDao.insert(bank)
    }

    suspend fun getAllBanks(): List<BankEntity> {
        return bankDao.getAll()
    }

    // Комплексные операции
    suspend fun insertFullCardInfo(
        cardNumber: CardNumberEntity,
        country: CountryEntity,
        bank: BankEntity,
        cardInfo: CardInfoEntity
    ) {
        // Вставляем CardNumber, Country и Bank, чтобы получить их ID
        val cardNumberId = insertCardNumber(cardNumber)
        val countryId = insertCountry(country)
        val bankId = insertBank(bank)

        // Устанавливаем полученные ID в CardInfo
        val fullCardInfo = cardInfo.copy(
            cardNumberId = cardNumberId,
            countryId = countryId,
            bankId = bankId
        )

        // Вставляем CardInfo
        insertCardInfo(fullCardInfo)
    }
}