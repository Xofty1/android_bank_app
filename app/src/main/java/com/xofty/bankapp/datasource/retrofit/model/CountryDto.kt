package com.xofty.bankapp.datasource.retrofit.model

data class CountryDto(
    val numeric: String?,
    val alpha2: String?,
    val name: String?,
    val emoji: String?,
    val currency: String?,
    val latitude: Int,
    val longitude: Int
)