package com.example.spotifyapp.network

import com.example.spotifyapp.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.Protocol
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitInstance {

    val api: ApiService by lazy {
        val client = OkHttpClient.Builder()
            .followRedirects(false)
            .followSslRedirects(false)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .protocols(listOf(Protocol.HTTP_1_1))
            .addInterceptor { chain ->
                val response = chain.proceed(chain.request())
                if (response.code in 301..302) {
                    val newUrl = response.header("Location") ?: return@addInterceptor response
                    response.close()
                    chain.proceed(chain.request().newBuilder().url(newUrl).build())
                } else {
                    response
                }
            }
            .build()

        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}