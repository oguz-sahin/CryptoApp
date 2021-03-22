package com.example.cryptoapp.data.source

import com.example.cryptoapp.network.CryptoService
import javax.inject.Inject

class CoinDetailDataSource @Inject constructor(private val cryptoService: CryptoService) :
    BaseRemoteDataSource() {
    suspend fun getCryptoCurrencyById(cryptoId: String) =
        getResult { cryptoService.getCryptoCurrencyById(cryptoId) }
}