package com.example.cryptoapp.data.repository

import com.example.cryptoapp.data.source.CoinDetailDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CoinDetailRepository @Inject constructor(
    private val remoteDataSource: CoinDetailDataSource
) {

    fun getCoinDetailById(coinId: String) = flow {
        val result = remoteDataSource.getCryptoCurrencyById(coinId)
        emit(result)
    }.flowOn(Dispatchers.IO)
}