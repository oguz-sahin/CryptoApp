package com.example.cryptoapp.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.cryptoapp.data.Status.*
import com.example.cryptoapp.databinding.FragmentFavoritesBinding
import com.example.cryptoapp.util.extensions.showErrorToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : Fragment() {
    private val favoritesViewModel: FavoritesViewModel by viewModels()
    private lateinit var binding: FragmentFavoritesBinding
    private val favoriteCoinListAdapter = FavoriteCoinListAdapter()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false).also {
            it.lifecycleOwner = viewLifecycleOwner
            it.recyclerviewFavoriteCoins.adapter = favoriteCoinListAdapter
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initObserver()
    }

    private fun initObserver() {
        favoritesViewModel.favoriteCoinList.observe(viewLifecycleOwner, {
            it?.let {
                when (it.status) {
                    LOADING -> Unit
                    SUCCESS -> {
                        favoriteCoinListAdapter.submitList(it.data)
                    }
                    ERROR -> showErrorToast(it.message, Toast.LENGTH_LONG)
                }
                binding.isLoadingStatus = it.status == LOADING
            }
        })
    }
}