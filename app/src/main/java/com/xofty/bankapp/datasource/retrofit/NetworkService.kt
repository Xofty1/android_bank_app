package com.xofty.bankapp.datasource.retrofit

import android.util.Log
import com.google.gson.GsonBuilder
import com.xofty.bankapp.datasource.retrofit.model.CardInfo
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class NetworkService {

    private val BASE_URL = "https://lookup.binlist.net/"
    private val client: OkHttpClient by lazy {
        val itersector = HttpLoggingInterceptor()
        itersector.level = HttpLoggingInterceptor.Level.BODY

        OkHttpClient.Builder()
            .addInterceptor(itersector)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }
    private val retrofit: Retrofit by lazy {
        val gson = GsonBuilder().setLenient().create()
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
    }


    private val binApi: BinApi by lazy {
        retrofit.create(BinApi::class.java)
    }

    fun getCardInfo(bin: Int): CardInfo? {
        var cardInfo: CardInfo? = null
        binApi.getCardInfo(bin).enqueue(object : Callback<CardInfo> {
            override fun onResponse(call: Call<CardInfo>, response: Response<CardInfo>) {
                if (response.isSuccessful) {
                    cardInfo = response.body()
                    if (cardInfo != null) {
                        Log.d("getCardInfo", "Card info: $cardInfo")
                    } else {
                        Log.e("getCardInfo", "Response body is null")
                    }
                } else {
                    Log.e("getCardInfo", "Request failed: ${response.code()} - ${response.message()}")
                }
            }

            override fun onFailure(call: Call<CardInfo>, t: Throwable) {
                Log.e("getCardInfo", "Network request failed", t)
            }
        })
        return cardInfo
    }
}

