package com.example.mymovieapp.data.services

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceBuilder {
    private var retrofit: Retrofit? = null
    private var retrofit2: Retrofit? = null
    private var logger: HttpLoggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    private var okHttp = OkHttpClient.Builder().addInterceptor(logger).build()

    private fun getClient(baseUrl: String = "https://api.themoviedb.org/3/"): Retrofit {
        retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttp)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit as Retrofit
    }

    private fun getClientOMDB(baseUrl: String = "http://www.omdbapi.com"): Retrofit {
        val gson: Gson = GsonBuilder()
                .setLenient()
                .create()

        retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttp)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        return retrofit as Retrofit
    }

    fun <T> buildService(contract: Class<T>): T {
        return getClient().create(contract)
    }

    fun <T> buildServiceOMDB(contract: Class<T>): T {
        return getClientOMDB().create(contract)
    }
}