package com.scurab.dipho.android.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.scurab.dipho.android.R
import com.scurab.dipho.android.databinding.FragmentHomeBinding
import com.scurab.dipho.home.HomeViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HomeFragment : Fragment(R.layout.fragment_home) {

    //TODO: lazy
    private var views: FragmentHomeBinding? = null
    private val viewModel: HomeViewModel by viewModels()
    private val adapter = HomeAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        views = FragmentHomeBinding.bind(view)

        bindViews()
        bindViewModel()
    }

    private fun bindViews() = with(views!!) {
        recyclerView.also {
            it.adapter = adapter
            it.layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun bindViewModel() {
        viewModel.uiState.observe {
            adapter.items = it.items
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadItemsAsync()
    }
}