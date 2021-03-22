package com.example.cryptoapp.data.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "crypto_currencies")
data class CryptoModelItem(
    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    val cryptoId: String,

    @ColumnInfo(name = "name")
    @SerializedName("name")
    var name: String?,

    @ColumnInfo(name = "symbol")
    @SerializedName("symbol")
    var symbol: String?,

    @ColumnInfo(name = "image")
    @SerializedName("image")
    val cryptoImageUrl: String?,

    @ColumnInfo(name = "currentPrice")
    @SerializedName("current_price")
    val currentPrice: Double?,

    @ColumnInfo(name = "highForDayHour")
    @SerializedName("high_24h")
    val highForDayHour: Double?,

    @ColumnInfo(name = "lowForDayHour")
    @SerializedName("low_24h")
    val lowForDayHour: Double?,

    @ColumnInfo(name = "lastUpdated")
    @SerializedName("last_updated")
    val lastUpdated: String?,

    @ColumnInfo(name = "priceChangeForDayHour")
    @SerializedName("price_change_24h")
    val priceChangeForDayHour: Double?,

    @ColumnInfo(name = "priceChangePercentageForDayHour")
    @SerializedName("price_change_percentage_24h")
    val priceChangePercentageForDayHour: Double?

)