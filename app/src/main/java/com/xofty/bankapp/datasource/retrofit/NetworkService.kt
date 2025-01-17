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
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class NetworkService {

    private val BASE_URL = "https://lookup.binlist.net/"
    private val client: OkHttpClient by lazy {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        OkHttpClient.Builder()
            .addInterceptor(interceptor)
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

    suspend fun getCardInfo(bin: Int): Result<CardInfo> {
        return try {
            val response = binApi.getCardInfo(bin).awaitResponse()
            if (response.isSuccessful) {
                response.body()?.let {
                    Log.d("getCardInfo", "Card info: $it")
                    Result.success(it)
                } ?: Result.failure(Exception("Empty response body"))
            } else {
                Log.e("getCardInfo", "Request failed: ${response.code()} - ${response.message()}")
                Result.failure(Exception("Request failed: ${response.code()} - ${response.message()}"))
            }
        } catch (e: Exception) {
            Log.e("getCardInfo", "Network request failed", e)
            Result.failure(e)
        }
    }
}