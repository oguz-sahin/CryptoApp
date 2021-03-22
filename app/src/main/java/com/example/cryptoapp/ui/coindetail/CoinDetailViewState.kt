package com.example.cryptoapp.ui.coindetail

import android.view.View
import com.example.cryptoapp.data.Status
import com.example.cryptoapp.data.Status.LOADING
import com.example.cryptoapp.data.model.CoinDetailModel
import com.example.cryptoapp.util.upperCase

data class CoinDetailViewState(
    private val coinDetail: CoinDetailModel?,
    private val status: Status
) {

    fun getCoinImageUrl() = coinDetail?.image?.thumb

    fun getCoinName() = coinDetail?.name.upperCase()

    fun getCoinSymbol() = coinDetail?.symbol.upperCase()

    fun getCoinHashAlgorithm() = coinDetail?.hashing_algorithm

    fun getCoinChangePercentageForDayHour() = coinDetail?.priceChangePercentageForDayHour

    fun getCoinCurrentPrice() = coinDetail?.market_data?.current_price

    fun getCoinDescription() = coinDetail?.description?.en

    fun getLoadingVisibility() = if (status == LOADING) View.VISIBLE else View.GONE

    fun getPageVisibility() = if (status != LOADING) View.VISIBLE else View.GONE
}
