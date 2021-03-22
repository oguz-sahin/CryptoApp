package com.example.cryptoapp.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cryptoapp.data.dto.CryptoModelItem

@Dao
interface CryptoCurrenciesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(cryptoCurrencies: List<CryptoModelItem>)

    @Query("SELECT * FROM CRYPTO_CURRENCIES WHERE name LIKE :search OR symbol LIKE :search")
    suspend fun getCryptoCurrenciesByQuery(search: String): List<CryptoModelItem>
}