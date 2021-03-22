package com.example.cryptoapp.network

import com.example.cryptoapp.data.dto.CryptoModelItem
import com.example.cryptoapp.data.model.CoinDetailModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CryptoService {

    @GET("coins/markets")
    suspend fun getCryptoCurrencies(@Query("vs_currency") currency: String = "usd"): Response<List<CryptoModelItem>>

    @GET("coins/{id}")
    suspend fun getCryptoCurrencyById(@Path("id") cryptoId: String): Response<CoinDetailModel>

}