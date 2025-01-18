package com.xofty.bankapp.domain.mapper.databaseDomain

import com.xofty.bankapp.datasource.room.entity.BankEntity
import com.xofty.bankapp.domain.model.Bank

fun Bank.toEntity(): BankEntity {
    return BankEntity(
        name = this.name,
        url = this.url,
        phone = this.phone,
        city = this.city
    )
}

fun BankEntity.toDomain(): Bank {
    return Bank(
        name = this.name,
        url = this.url,
        phone = this.phone,
        city = this.city
    )
}