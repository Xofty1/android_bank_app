package com.xofty.bankapp.domain.mapper.dtoDomain

import com.xofty.bankapp.datasource.retrofit.model.CardNumberDto
import com.xofty.bankapp.domain.model.CardNumber

fun CardNumberDto.toDomain(): CardNumber {
    return CardNumber(
        luhn = this.luhn,
        length = this.length
    )
}