package com.example.certificationdevspaceapp.data.model

import com.google.gson.annotations.SerializedName

data class CountryDto(
    val name: NameDto,
    val capital: List<String>?,
    val flags: FlagsDto,
    val region: String?,
    val languages: Map<String, String>?,
    val currencies: Map<String, CurrencyDto>?,
    val population: Long?,
    @SerializedName("cca2") val cca2: String?,
    @SerializedName("cca3") val cca3: String?
)

data class NameDto (
    val common: String
)

data class FlagsDto (
    val png: String
)

data class CurrencyDto(
    val name: String?,
    val symbol: String?
)