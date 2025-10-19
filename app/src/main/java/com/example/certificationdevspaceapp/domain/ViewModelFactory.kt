package com.example.certificationdevspaceapp.domain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.certificationdevspaceapp.data.repository.Repository
import com.example.certificationdevspaceapp.domain.usecase.GetAllCountriesUseCase
import com.example.certificationdevspaceapp.domain.usecase.GetCountryByCodeUseCase
import com.example.certificationdevspaceapp.presentation.model.CountryDetailViewModel
import com.example.certificationdevspaceapp.presentation.model.CountryListViewModel

class ViewModelFactory(
    private val getAllCountriesUseCase: GetAllCountriesUseCase,
    private val getCountryByCodeUseCase: GetCountryByCodeUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(CountryListViewModel::class.java) ->
                CountryListViewModel(getAllCountriesUseCase) as T

            modelClass.isAssignableFrom(CountryDetailViewModel::class.java) ->
                CountryDetailViewModel(getCountryByCodeUseCase) as T

            else -> throw IllegalArgumentException(ERROR_UNKNOWN_VIEWMODEL)
        }
    }
}
const val ERROR_UNKNOWN_VIEWMODEL = "Unknown ViewModel class"