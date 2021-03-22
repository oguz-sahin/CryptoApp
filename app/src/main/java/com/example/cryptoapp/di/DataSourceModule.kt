package com.example.cryptoapp.di

import com.example.cryptoapp.data.source.CoinDetailDataSource
import com.example.cryptoapp.data.source.HomeRemoteDataSource
import com.example.cryptoapp.network.CryptoService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {


    @Provides
    fun provideHomeRemoteDataSource(cryptoService: CryptoService) =
        HomeRemoteDataSource(cryptoService)

    @Provides
    fun provideCoinDetailRemoteDataSource(cryptoService: CryptoService) =
        CoinDetailDataSource(cryptoService)

}