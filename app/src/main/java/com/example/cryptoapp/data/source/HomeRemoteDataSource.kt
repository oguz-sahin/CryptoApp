package com.example.cryptoapp.data.source

import com.example.cryptoapp.network.CryptoService
import javax.inject.Inject

class HomeRemoteDataSource @Inject constructor(private val cryptoService: CryptoService) :
    BaseRemoteDataSource() {
    suspend fun getCryptoCurrencies() = getResult { cryptoService.getCryptoCurrencies() }
}