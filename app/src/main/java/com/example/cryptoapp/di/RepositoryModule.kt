package com.example.cryptoapp.di

import com.example.cryptoapp.data.local.CryptoCurrenciesDao
import com.example.cryptoapp.data.repository.CoinDetailRepository
import com.example.cryptoapp.data.repository.HomeRepository
import com.example.cryptoapp.data.source.CoinDetailDataSource
import com.example.cryptoapp.data.source.HomeRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideHomeRepository(
        homeRemoteDataSource: HomeRemoteDataSource,
        homeLocalDataSource: CryptoCurrenciesDao
    ) = HomeRepository(homeRemoteDataSource, homeLocalDataSource)

    @Provides
    fun provideCoinDetailRepository(
        coinDetailRemoteDataSource: CoinDetailDataSource
    ) = CoinDetailRepository(coinDetailRemoteDataSource)
}