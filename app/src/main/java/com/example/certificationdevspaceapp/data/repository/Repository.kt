package com.example.certificationdevspaceapp.data.repository

import android.util.Log
import com.example.certificationdevspaceapp.data.model.CountryDto
import com.example.certificationdevspaceapp.data.network.Api

class Repository(private val api: Api) {
    suspend fun getAllCountries(): List<CountryDto> =
        api.getAllCountries()

    suspend fun getCountryByCode(code: String): CountryDto? {
        val url =
            "https://restcountries.com/v3.1/alpha/$code?fields=name,capital,flags,region,languages,currencies,population,cca2,cca3"
        Log.d("Repository", "Full URL: $url")
        return api.getCountryByCode(url)
    }

}