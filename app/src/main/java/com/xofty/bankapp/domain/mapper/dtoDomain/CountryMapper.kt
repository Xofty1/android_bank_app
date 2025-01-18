package com.xofty.bankapp.domain.mapper.dtoDomain

import com.xofty.bankapp.datasource.retrofit.model.CountryDto
import com.xofty.bankapp.domain.model.Country

fun CountryDto.toDomain(): Country {
    return Country(
        numeric = this.numeric,
        alpha2 = this.alpha2,
        name = this.name,
        emoji = this.emoji,
        currency = this.currency,
        latitude = this.latitude,
        longitude = this.longitude
    )
}