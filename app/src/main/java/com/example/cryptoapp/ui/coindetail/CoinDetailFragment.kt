package com.example.cryptoapp.ui.coindetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.cryptoapp.R
import com.example.cryptoapp.data.Status.*
import com.example.cryptoapp.databinding.FragmentCoinDetailBinding
import com.example.cryptoapp.util.extensions.showErrorToast
import com.example.cryptoapp.util.setVisibility
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CoinDetailFragment : Fragment() {
    private val coinIdArgs: CoinDetailFragmentArgs by navArgs()

    @Inject
    lateinit var coinDetailViewModelFactory: CoinDetailViewModel.AssistedFactory
    private val coinDetailViewModel: CoinDetailViewModel by viewModels {
        CoinDetailViewModel.provideFactory(
            coinDetailViewModelFactory, coinIdArgs.coinId
        )
    }
    private lateinit var binding: FragmentCoinDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCoinDetailBinding.inflate(inflater, container, false).also {
            it.lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initObserver()
        initClickEvents()
    }

    private fun initObserver() {
        coinDetailViewModel.coinDetail.observe(viewLifecycleOwner, {
            when (it.status) {
                LOADING -> Unit
                SUCCESS -> Unit
                ERROR -> showErrorToast(it.message, Toast.LENGTH_LONG)
            }
            binding.coinDetailViewState = CoinDetailViewState(it.data, it.status)
        })

        coinDetailViewModel.isAddFavorite.observe(viewLifecycleOwner, { isAdded ->
            isAdded?.let {
                when (it.status) {
                    LOADING -> Unit
                    SUCCESS -> {
                        binding.imageViewAddFavorite.setImageResource(R.drawable.ic_favorite_red)
                    }
                    ERROR -> {
                        showErrorToast(it.message, Toast.LENGTH_LONG)
                    }
                }
                setViewState(it.status == LOADING)
            }
        })
    }

    private fun initClickEvents() {
        binding.imageViewAddFavorite.setOnClickListener {
            coinDetailViewModel.addFavorite()
        }
    }

    private fun setViewState(isLoadingStatus: Boolean) {
        with(binding) {
            progressBar.setVisibility(isLoadingStatus)
            constraintLayout.setVisibility(isLoadingStatus.not())
        }
    }
}