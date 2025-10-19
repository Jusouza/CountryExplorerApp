package com.example.certificationdevspaceapp.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.certificationdevspaceapp.data.network.RetrofitClient
import com.example.certificationdevspaceapp.data.repository.Repository
import com.example.certificationdevspaceapp.databinding.CountryDetailFragmentBinding
import com.example.certificationdevspaceapp.domain.ViewModelFactory
import com.example.certificationdevspaceapp.domain.usecase.GetAllCountriesUseCase
import com.example.certificationdevspaceapp.domain.usecase.GetCountryByCodeUseCase
import com.example.certificationdevspaceapp.presentation.model.CountryDetailViewModel
import com.example.certificationdevspaceapp.utils.FormatterUtils.formatPopulation

class CountryDetailFragment : Fragment() {
    private var _binding: CountryDetailFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: CountryDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CountryDetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModel()
        setupObservers()
        setupToolbar()

        val args = CountryDetailFragmentArgs.fromBundle(requireArguments())
        viewModel.loadCountry(args.code)
    }

    private fun setupViewModel() {
        val api = RetrofitClient.api
        val repository = Repository(api)
        val getCountryByCodeUseCase = GetCountryByCodeUseCase(repository)
        val getAllCountriesUseCase = GetAllCountriesUseCase(repository)
        val factory = ViewModelFactory(getAllCountriesUseCase, getCountryByCodeUseCase)
        viewModel = ViewModelProvider(this, factory)[CountryDetailViewModel::class.java]
    }

    private fun setupObservers() {
        viewModel.country.observe(viewLifecycleOwner) { country ->
            with(binding) {
                Glide.with(this@CountryDetailFragment)
                    .load(country.flagUrl)
                    .into(binding.flagDetailImg)

                tvCountyName.text = country.name
                tvCapitalResult.text = country.capital
                tvRegionResult.text = country.region
                tvPopulationResult.text = formatPopulation(country.population)
                tvLanguageResult.text = country.language
                tvCurrencyResult.text = country.currency
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            binding.progressBar.isVisible = it
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            if (error != null) {
                Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}