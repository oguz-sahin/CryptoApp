package com.example.cryptoapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptoapp.data.Status.*
import com.example.cryptoapp.databinding.FragmentHomeBinding
import com.example.cryptoapp.util.extensions.showErrorToast
import com.example.cryptoapp.util.getQueryTextChangeStateFlow
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var binding: FragmentHomeBinding
    private val coinListAdapter = CoinListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false).also {
            it.lifecycleOwner = viewLifecycleOwner
            it.recyclerViewCoinList.adapter = coinListAdapter
        }
        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initObserver()
        setAdapterProperties()
        setEditTextSearchProperties()
    }

    private fun setAdapterProperties() {
        coinListAdapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                (binding.recyclerViewCoinList.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(
                    positionStart,
                    0
                )
            }
        })

        coinListAdapter.setOnItemCoinClickListener { coinId ->
            val action = HomeFragmentDirections.actionHomeFragmentToCoinDetailFragment(coinId)
            findNavController().navigate(action)
        }
    }


    private fun initObserver() {
        homeViewModel.cryptoCurrencies.observe(viewLifecycleOwner, {
            when (it.status) {
                LOADING -> Unit
                SUCCESS -> coinListAdapter.submitList(it.data)
                ERROR -> showErrorToast(it.message, Toast.LENGTH_LONG)
            }
            binding.isLoadingStatus = it.status == LOADING
        })
    }


    private fun setEditTextSearchProperties() {
        lifecycleScope.launch {
            binding.editTextSearch.getQueryTextChangeStateFlow()
                .debounce(300)
                .distinctUntilChanged()
                .filter { query ->
                    if (query.isEmpty()) {
                        coinListAdapter.submitList(homeViewModel.cryptoCurrencies.value?.data)
                        return@filter false
                    } else {
                        return@filter true
                    }
                }.flatMapLatest { query ->
                    homeViewModel.search(query)
                        .catch { emitAll(flowOf(listOf())) }
                }.collect {
                    coinListAdapter.submitList(it)
                }
        }
    }

}