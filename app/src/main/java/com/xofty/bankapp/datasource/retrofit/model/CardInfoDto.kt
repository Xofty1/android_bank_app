package com.xofty.bankapp.datasource.retrofit.model


data class CardInfoDto(
    var number: CardNumberDto?,
    val scheme: String?,
    val type: String?,
    val brand: String?,
    val prepaid: Boolean?,
    val country: CountryDto?,
    var bank: BankDto?
)