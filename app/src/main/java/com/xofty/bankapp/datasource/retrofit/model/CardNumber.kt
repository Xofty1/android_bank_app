package com.xofty.bankapp.datasource.retrofit.model

data class CardNumber(
    val length: Int,
    val luhn: Boolean
)