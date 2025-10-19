package com.example.certificationdevspaceapp.presentation.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.certificationdevspaceapp.domain.model.CountryDomain
import com.example.certificationdevspaceapp.domain.usecase.GetCountryByCodeUseCase
import com.example.certificationdevspaceapp.presentation.data.CountryUiState
import kotlinx.coroutines.launch

class CountryDetailViewModel(
    private val getCountryByCodeUseCase: GetCountryByCodeUseCase
) : ViewModel() {

    private val _country = MutableLiveData<CountryUiState>()
    val country: LiveData<CountryUiState> = _country

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun loadCountry(code: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            val result = getCountryByCodeUseCase(code)
            result.fold(
                onSuccess = { domain ->
                    _country.value = domain.toUiState()
                },
                onFailure = { e ->
                    _error.value = e.message ?: "Error loading country details"
                }
            )

            _isLoading.value = false
        }
    }

    private fun CountryDomain.toUiState() = CountryUiState(
        name = name,
        capital = capital,
        region = region,
        flagUrl = flagUrl,
        population = population,
        language = language,
        currency = currency,
        code = code,
        isFavorite = false
    )

}
