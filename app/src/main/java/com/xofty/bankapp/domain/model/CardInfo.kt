package com.xofty.bankapp.domain.model

import java.io.Serializable


data class CardInfo(
    val number: CardNumber,
    val scheme: String,
    val type: String,
    val brand: String,
    val prepaid: Boolean,
    val country: Country,
    val bank: Bank
): Serializable