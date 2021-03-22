package com.example.cryptoapp.ui.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.cryptoapp.data.Resource
import com.example.cryptoapp.data.Resource.Companion.error
import com.example.cryptoapp.data.Resource.Companion.loading
import com.example.cryptoapp.data.Resource.Companion.success
import com.example.cryptoapp.data.model.CoinDetailModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.hadilq.liveevent.LiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFireStore: FirebaseFirestore
) : ViewModel() {

    private val _favoriteCoinList = LiveEvent<Resource<List<CoinDetailModel>>>()
    val favoriteCoinList: LiveData<Resource<List<CoinDetailModel>>> get() = _favoriteCoinList

    init {
        getFavoriteCoinList()
    }

    private fun getFavoriteCoinList() {
        _favoriteCoinList.postValue(loading())
        firebaseFireStore.collection(firebaseAuth.currentUser!!.uid)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val favoriteCoinList = mutableListOf<CoinDetailModel>()
                    task.result?.forEach {
                        favoriteCoinList.add(it.toObject(CoinDetailModel::class.java))
                    }
                    _favoriteCoinList.postValue(success(favoriteCoinList.distinctBy { it.symbol }))
                } else {
                    _favoriteCoinList.postValue(error(task.exception?.message))
                }
            }
    }

}