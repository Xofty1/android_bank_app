package com.xofty.bankapp.datasource.retrofit.model


data class CardNumberDto(
    val length: Int,
    val luhn: Boolean
)