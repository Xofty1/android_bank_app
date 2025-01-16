package com.xofty.bankapp.datasource.retrofit.model


data class CardInfo(
    val number: CardNumber,
    val scheme: String,
    val type: String,
    val brand: String,
    val prepaid: Boolean,
    val country: Country,
    val bank: Bank
)