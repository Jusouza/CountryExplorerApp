package com.example.certificationdevspaceapp.presentation.data

data class CountryUiState(
    val name: String,
    val capital: String,
    val region: String,
    val flagUrl: String,
    val population: Long,
    val code: String,
    val isFavorite: Boolean = false
)