package com.xofty.bankapp.datasource.retrofit

import retrofit2.Call
import com.xofty.bankapp.datasource.retrofit.model.CardInfo
import retrofit2.http.GET
import retrofit2.http.Path

interface BinApi {
    @GET("{id}")
    fun getCardInfo(@Path("id") bin: Int): Call<CardInfo>
}