package com.example.certificationdevspaceapp.presentation.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
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

class CountryListFragment: Fragment() {
    private var _binding: CountryListFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: CountryListViewModel
    private lateinit var adapter: CountryListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = CountryListFragmentBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecycler()
        goToFavoritesList()
        setupViewModel()
        observeUiState()

        viewModel.loadCountries()
    }

    private fun setupViewModel() {
        val api = RetrofitClient.api
        val repository = Repository(api)
        val getAllCountriesUseCase = GetAllCountriesUseCase(repository)
        val getCountryByCodeUseCase = GetCountryByCodeUseCase(repository)

        val factory = ViewModelFactory(getAllCountriesUseCase, getCountryByCodeUseCase)
        viewModel = ViewModelProvider(this, factory)[CountryListViewModel::class.java]
    }
    private fun setupRecycler() {
        adapter = CountryListAdapter(
            onItemClick = { country ->
                Log.d("DEBUG_CODE", "Country clicked: ${country.name} -> ${country.code}")

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
            Log.d("FRAGMENT", "Recycler received ${countries.size} items")

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

    private fun goToFavoritesList(){
        binding.btnFavorite.setOnClickListener {
            findNavController().navigate(R.id.action_countryListFragment_to_favoritesListFragment)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}