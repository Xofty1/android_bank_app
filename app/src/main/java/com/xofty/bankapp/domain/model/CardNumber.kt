package com.xofty.bankapp.domain.model

data class CardNumber(
    val length: Int,
    val luhn: Boolean
)