package com.example.certificationdevspaceapp.presentation.data

sealed interface CountryListState {
    data object Loading : CountryListState
    data class Success(val data: List<CountryUiState>) : CountryListState
    data class Error(val kind: ErrorKind, val httpCode: Int? = null) : CountryListState
}

enum class ErrorKind { NO_INTERNET, HTTP, UNKNOWN }