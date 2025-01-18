package com.xofty.bankapp.domain.mapper.dtoDomain

import com.xofty.bankapp.datasource.retrofit.model.CardInfoDto
import com.xofty.bankapp.domain.model.CardInfo

fun CardInfoDto.toDomain(): CardInfo {
    return CardInfo(
        number = this.number?.toDomain(),
        scheme = this.scheme,
        type = this.type,
        brand = this.brand,
        prepaid = this.prepaid,
        country = this.country?.toDomain(),
        bank = this.bank?.toDomain()
    )
}