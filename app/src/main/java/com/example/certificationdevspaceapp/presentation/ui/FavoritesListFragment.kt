package com.example.certificationdevspaceapp.presentation.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.certificationdevspaceapp.data.network.RetrofitClient
import com.example.certificationdevspaceapp.data.repository.Repository
import com.example.certificationdevspaceapp.databinding.CountryFavoritesFragmentBinding
import com.example.certificationdevspaceapp.domain.ViewModelFactory
import com.example.certificationdevspaceapp.domain.usecase.GetAllCountriesUseCase
import com.example.certificationdevspaceapp.domain.usecase.GetCountryByCodeUseCase
import com.example.certificationdevspaceapp.presentation.adapter.CountryListAdapter
import com.example.certificationdevspaceapp.presentation.data.CountryListState
import com.example.certificationdevspaceapp.presentation.model.CountryListViewModel

class FavoritesListFragment : Fragment() {

    private var _binding: CountryFavoritesFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CountryListViewModel by activityViewModels {
        val api = RetrofitClient.api
        val repository = Repository(api)
        val getAllCountriesUseCase = GetAllCountriesUseCase(repository)
        val getCountryByCodeUseCase = GetCountryByCodeUseCase(repository)
        ViewModelFactory(getAllCountriesUseCase, getCountryByCodeUseCase)
    }

    private lateinit var adapter: CountryListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CountryFavoritesFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecycler()
        observeFavorites()
        setupToolbar()
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setupRecycler() {
        adapter = CountryListAdapter(
            onItemClick = { country ->
                val action = FavoritesListFragmentDirections
                    .actionFavoritesListFragmentToCountryDetailFragment(country.code)
                findNavController().navigate(action)
            },
            onFavoriteClick = { country ->
                viewModel.toggleFavorite(country)
            }
        )

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
        binding.recyclerView.setHasFixedSize(true)
    }

    private fun observeFavorites() {
        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            if (state is CountryListState.Success) {
                val favorites = state.data.filter { it.isFavorite }
                binding.recyclerView.isVisible = favorites.isNotEmpty()
                binding.emptyStateText.isVisible = favorites.isEmpty()
                adapter.submitList(favorites)
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}