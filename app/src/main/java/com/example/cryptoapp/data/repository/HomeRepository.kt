package com.example.cryptoapp.data.repository

import com.example.cryptoapp.data.Resource
import com.example.cryptoapp.data.Status.SUCCESS
import com.example.cryptoapp.data.dto.CryptoModelItem
import com.example.cryptoapp.data.local.CryptoCurrenciesDao
import com.example.cryptoapp.data.source.HomeRemoteDataSource
import com.example.cryptoapp.util.convertStringForSearchQuery
import com.example.cryptoapp.util.upperCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val remoteDataSource: HomeRemoteDataSource,
    private val localDataSource: CryptoCurrenciesDao
) {

    fun getCryptoCurrencies() = flow<Resource<List<CryptoModelItem>>> {
        val result = remoteDataSource.getCryptoCurrencies()
        emit(result)
    }.map {
        it.data?.forEach { cryptoModelItem ->
            cryptoModelItem.symbol = cryptoModelItem.symbol?.upperCase()
        }
        if (it.status == SUCCESS && it.data != null) {
            localDataSource.insertAll(it.data)
        }
        it
    }.flowOn(Dispatchers.IO)


    fun getCryptoCurrenciesByQuery(query: String) = flow<List<CryptoModelItem>> {
        val result = localDataSource.getCryptoCurrenciesByQuery(query.convertStringForSearchQuery())
        emit(result)
    }.flowOn(Dispatchers.IO)


}