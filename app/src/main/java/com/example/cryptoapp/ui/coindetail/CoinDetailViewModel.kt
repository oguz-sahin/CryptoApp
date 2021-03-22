package com.example.cryptoapp.ui.coindetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.cryptoapp.data.Resource
import com.example.cryptoapp.data.Resource.Companion.loading
import com.example.cryptoapp.data.Resource.Companion.success
import com.example.cryptoapp.data.model.CoinDetailModel
import com.example.cryptoapp.data.repository.CoinDetailRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.hadilq.liveevent.LiveEvent
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class CoinDetailViewModel @AssistedInject constructor(
    @Assisted private val coinId: String,
    private val coinDetailRepository: CoinDetailRepository,
    private val firebaseFireStore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth
) :
    ViewModel() {

    private val _coinDetail = LiveEvent<Resource<CoinDetailModel>>()
    val coinDetail: LiveData<Resource<CoinDetailModel>> get() = _coinDetail

    private val _isAddedFavorite = LiveEvent<Resource<Boolean>>()
    val isAddFavorite: LiveData<Resource<Boolean>> get() = _isAddedFavorite

    init {
        getCoinDetail()
    }

    private fun getCoinDetail() {
        viewModelScope.launch {
            coinDetailRepository
                .getCoinDetailById(coinId)
                .onStart { emit(loading()) }
                .collect {
                    _coinDetail.postValue(it)
                }

        }
    }

    fun addFavorite() {
        if (isAddFavorite.value?.data != true) {
            _isAddedFavorite.postValue(loading())
            coinDetail.value?.data?.let {
                firebaseFireStore
                    .collection(firebaseAuth.currentUser!!.uid)
                    .add(it)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            _isAddedFavorite.postValue(success(true))
                        } else {
                            _isAddedFavorite.postValue(Resource.error(task.exception?.message))
                        }
                    }
            }
        }
    }


    @dagger.assisted.AssistedFactory
    interface AssistedFactory {
        fun create(coinId: String): CoinDetailViewModel
    }

    companion object {
        fun provideFactory(
            assistedFactory: AssistedFactory,
            coinId: String
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return assistedFactory.create(coinId) as T
            }
        }
    }


}