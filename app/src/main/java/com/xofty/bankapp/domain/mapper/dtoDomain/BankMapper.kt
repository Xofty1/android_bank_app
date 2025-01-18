package com.xofty.bankapp.domain.mapper.dtoDomain

import com.xofty.bankapp.datasource.retrofit.model.BankDto
import com.xofty.bankapp.domain.model.Bank

fun BankDto.toDomain(): Bank {
    return Bank(
        name = this.name,
        url = this.url,
        phone = this.phone,
        city = this.city
    )
}