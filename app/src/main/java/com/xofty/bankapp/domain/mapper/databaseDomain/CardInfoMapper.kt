package com.xofty.bankapp.domain.mapper.databaseDomain

import android.util.Log
import com.xofty.bankapp.datasource.room.dao.BankDao
import com.xofty.bankapp.datasource.room.dao.CardNumberDao
import com.xofty.bankapp.datasource.room.dao.CountryDao
import com.xofty.bankapp.datasource.room.entity.CardInfoEntity
import com.xofty.bankapp.domain.model.CardInfo

fun CardInfo.toEntity(
    cardNumberId: Long,
    countryId: Long,
    bankId: Long
): CardInfoEntity {
    return CardInfoEntity(
        scheme = this.scheme,
        type = this.type,
        brand = this.brand,
        prepaid = this.prepaid,
        cardNumberId = cardNumberId,
        countryId = countryId,
        bankId = bankId
    )
}
suspend fun CardInfoEntity.toDomain(
    cardNumberDao: CardNumberDao,
    countryDao: CountryDao,
    bankDao: BankDao
): CardInfo {
    // Получаем CardNumber по ID
    val cardNumber = cardNumberDao.getById(this.cardNumberId)?.toDomain()

    // Получаем Country по ID
    val country = countryDao.getById(this.countryId)?.toDomain()

    // Получаем Bank по ID
    val bank = bankDao.getById(this.bankId)?.toDomain()

    // Создаем доменный объект CardInfo
    return CardInfo(
        number = cardNumber,
        scheme = this.scheme,
        type = this.type,
        brand = this.brand,
        prepaid = this.prepaid,
        country = country,
        bank = bank
    )
}