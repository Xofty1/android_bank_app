package com.xofty.bankapp.domain.mapper.databaseDomain

import com.xofty.bankapp.datasource.room.entity.CardNumberEntity
import com.xofty.bankapp.domain.model.CardNumber

fun CardNumber.toEntity(): CardNumberEntity {
    return CardNumberEntity(
        length = this.length,
        luhn = this.luhn
    )
}

fun CardNumberEntity.toDomain(): CardNumber {
    return CardNumber(
        length = this.length,
        luhn = this.luhn
    )
}
