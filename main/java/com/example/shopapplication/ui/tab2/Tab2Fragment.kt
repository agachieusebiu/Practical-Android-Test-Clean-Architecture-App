package com.example.shopapplication.ui.tab2

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shopapplication.databinding.FragmentListBinding
import com.example.shopapplication.ui.DetailActivity
import com.example.shopapplication.ui.adapters.ProductAdapter
import com.example.shopapplication.ui.common.UiProductState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/***
 * Same logic as Tab1Fragment
 * */
@AndroidEntryPoint
class Tab2Fragment : Fragment() {
    private var binding: FragmentListBinding? = null
    private val vm: Tab2ViewModel by viewModels()
    private val adapter = ProductAdapter { product ->
        val intent = Intent(requireContext(), DetailActivity::class.java)
        intent.run {
            putExtra("id", product.id)
            putExtra("name", product.name)
            putExtra("clients", product.clientCount)
        }
        startActivity(intent)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentListBinding.inflate(inflater, container, false).also { binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding?.run {
            recycler.layoutManager = LinearLayoutManager(requireContext())
            recycler.adapter = adapter
            swipe.setOnRefreshListener { vm.onPullToRefresh() }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.items.collect { state ->
                    binding?.run {
                        swipe.isRefreshing = state is UiProductState.Loading
                    }
                    if (state is UiProductState.Success) adapter.submitList(state.data)
                }
            }
        }
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}