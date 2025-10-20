package com.example.certificationdevspaceapp.presentation.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.certificationdevspaceapp.data.repository.Repository
import com.example.certificationdevspaceapp.domain.model.CountryDomain
import com.example.certificationdevspaceapp.domain.usecase.GetAllCountriesUseCase
import com.example.certificationdevspaceapp.presentation.data.CountryListState
import com.example.certificationdevspaceapp.presentation.data.CountryUiState
import com.example.certificationdevspaceapp.presentation.data.httpCodeOrNull
import com.example.certificationdevspaceapp.presentation.data.toError
import kotlinx.coroutines.launch

class CountryListViewModel(
    private val getAllCountriesUseCase: GetAllCountriesUseCase
) : ViewModel() {

    private val _uiState = MutableLiveData<CountryListState>(CountryListState.Loading)
    val uiState: LiveData<CountryListState> = _uiState

    private val favorites = mutableSetOf<String>()
    private var allCountries: List<CountryUiState> = emptyList()
    private var currentQuery: String = ""

    fun loadCountries() = viewModelScope.launch {
        _uiState.value = CountryListState.Loading
        val result = getAllCountriesUseCase()
        result.fold(
            onSuccess = { list ->
                allCountries = list.map {
                    CountryUiState(
                        name = it.name,
                        capital = it.capital,
                        region = it.region,
                        flagUrl = it.flagUrl,
                        population = it.population,
                        language = it.language,
                        currency = it.currency,
                        code = it.code,
                        isFavorite = favorites.contains(it.code)
                    )
                }
                _uiState.value = CountryListState.Success(allCountries)
            },
            onFailure = { e ->
                _uiState.value = CountryListState.Error(e.toError(), e.httpCodeOrNull())
            }
        )
    }

    fun filterCountries(query: String) {
        currentQuery = query
        val base = allCountries
        val filtered = if (query.isBlank()) base else base.filter {
            it.name.contains(query, ignoreCase = true) ||
                    it.region.contains(query, ignoreCase = true) ||
                    it.capital.contains(query, ignoreCase = true)
        }
        _uiState.value = CountryListState.Success(filtered)
    }

    fun toggleFavorite(item: CountryUiState) {
        if (favorites.contains(item.code)) favorites.remove(item.code) else favorites.add(item.code)

        allCountries = allCountries.map { it.copy(isFavorite = favorites.contains(it.code)) }

        filterCountries(currentQuery)
    }
}