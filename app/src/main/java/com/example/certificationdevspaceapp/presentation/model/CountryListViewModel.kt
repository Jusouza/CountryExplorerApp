package com.example.certificationdevspaceapp.presentation.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.certificationdevspaceapp.data.repository.Repository
import com.example.certificationdevspaceapp.domain.model.CountryDomain
import com.example.certificationdevspaceapp.domain.usecase.GetAllCountriesUseCase
import com.example.certificationdevspaceapp.presentation.data.CountryUiState
import kotlinx.coroutines.launch

class CountryListViewModel(
    private val getAllCountriesUseCase: GetAllCountriesUseCase
) : ViewModel() {

    private var _countries = MutableLiveData<List<CountryUiState>>()
    val countries: LiveData<List<CountryUiState>> = _countries

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    private val favorites = mutableSetOf<String>()

    fun loadCountries() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            Log.d("VIEWMODEL", "Loading countries...")

            val result = getAllCountriesUseCase()
            result.fold(
                onSuccess = { list ->
                    Log.d("VIEWMODEL", "Success: loaded ${list.size} countries")
                    list.forEach { Log.d("COUNTRY_ITEM", "${it.name} (${it.code})") }

                    _countries.value = list.map { it.toUiState() }
                },
                onFailure = { e ->
                    Log.e("VIEWMODEL", "Error: ${e.message}", e)
                    _error.value = e.message ?: "Unknown error"
                }
            )

            _isLoading.value = false
        }
    }

    fun toggleFavorite(item: CountryUiState) {
        _countries.value = _countries.value?.map {
            if (it.code == item.code) {
                val newState = !it.isFavorite
                if (newState) favorites.add(it.code) else favorites.remove(it.code)
                it.copy(isFavorite = newState)
            } else it
        }.orEmpty()
    }

    fun getFavoriteList(): List<CountryUiState> {
        return _countries.value?.filter { it.isFavorite } ?: emptyList()
    }

    private fun CountryDomain.toUiState() = CountryUiState(
        name = name,
        capital = capital,
        region = region,
        flagUrl = flagUrl,
        population = population,
        code = code,
        isFavorite = favorites.contains(code)
    )
}