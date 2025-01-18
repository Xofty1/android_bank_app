package com.xofty.bankapp.domain.mapper.databaseDomain

import com.xofty.bankapp.datasource.room.entity.CountryEntity
import com.xofty.bankapp.domain.model.Country

fun Country.toEntity(): CountryEntity {
    return CountryEntity(
        numeric = this.numeric,
        alpha2 = this.alpha2,
        name = this.name,
        emoji = this.emoji,
        currency = this.currency,
        latitude = this.latitude,
        longitude = this.longitude
    )
}

fun CountryEntity.toDomain(): Country {
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