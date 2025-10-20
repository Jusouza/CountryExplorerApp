package com.example.certificationdevspaceapp.presentation.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.certificationdevspaceapp.R
import com.example.certificationdevspaceapp.data.network.RetrofitClient
import com.example.certificationdevspaceapp.data.repository.Repository
import com.example.certificationdevspaceapp.databinding.CountryListFragmentBinding
import com.example.certificationdevspaceapp.domain.ViewModelFactory
import com.example.certificationdevspaceapp.domain.usecase.GetAllCountriesUseCase
import com.example.certificationdevspaceapp.domain.usecase.GetCountryByCodeUseCase
import com.example.certificationdevspaceapp.presentation.adapter.CountryListAdapter
import com.example.certificationdevspaceapp.presentation.model.CountryListViewModel

class CountryListFragment : Fragment() {
    private var _binding: CountryListFragmentBinding? = null
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
        _binding = CountryListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecycler()
        setupSearchBar()
        goToFavoritesList()
        observeUiState()

        if (viewModel.countries.value.isNullOrEmpty()) {
            viewModel.loadCountries()
        }
    }

    private fun setupRecycler() {
        adapter = CountryListAdapter(
            onItemClick = { country ->
                val action = CountryListFragmentDirections
                    .actionCountryListFragmentToCountryDetailFragment(country.code)
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

    private fun observeUiState() {
        viewModel.countries.observe(viewLifecycleOwner) { countries ->
            adapter.submitList(countries)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.isVisible = isLoading
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            if (error != null) {
                Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupSearchBar() {
        val searchView = binding.searchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.clearFocus()
                binding.recyclerView.scrollToPosition(0)
                query?.let { viewModel.filterCountries(it) }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.filterCountries(newText ?: "")
                return true
            }
        })
    }

    private fun goToFavoritesList() {
        binding.btnFavorite.setOnClickListener {
            findNavController().navigate(R.id.action_countryListFragment_to_favoritesListFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}