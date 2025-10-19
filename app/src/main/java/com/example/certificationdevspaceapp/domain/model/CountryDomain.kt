package com.example.certificationdevspaceapp.domain.model

data class CountryDomain(
    val name: String,
    val capital: String,
    val region: String,
    val flagUrl: String,
    val population: Long,
    val language: String,
    val currency: String,
    val code: String
)