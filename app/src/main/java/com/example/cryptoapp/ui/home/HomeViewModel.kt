package com.example.cryptoapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptoapp.data.Resource
import com.example.cryptoapp.data.Resource.Companion.loading
import com.example.cryptoapp.data.dto.CryptoModelItem
import com.example.cryptoapp.data.repository.HomeRepository
import com.hadilq.liveevent.LiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val homeRepository: HomeRepository) : ViewModel() {
    private val _cryptoCurrencies = LiveEvent<Resource<List<CryptoModelItem>>>()
    val cryptoCurrencies: LiveData<Resource<List<CryptoModelItem>>> get() = _cryptoCurrencies

    init {
        getCryptoCurrencies()
    }

    private fun getCryptoCurrencies() {
        viewModelScope.launch {
            homeRepository
                .getCryptoCurrencies()
                .onStart { emit(loading()) }
                .collect {
                    _cryptoCurrencies.postValue(it)
                }
        }
    }

    fun search(query: String) = homeRepository.getCryptoCurrenciesByQuery(query)
}