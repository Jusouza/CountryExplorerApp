package com.example.certificationdevspaceapp.data.repository

import android.util.Log
import com.example.certificationdevspaceapp.data.model.CountryDto
import com.example.certificationdevspaceapp.data.network.Api

class Repository(private val api: Api) {
    suspend fun getAllCountries(): List<CountryDto> =
        api.getAllCountries()

    suspend fun getCountryByCode(code: String): CountryDto? =
        try {
            api.getCountryByCode(code)
        } catch (e: Exception) {
            null
        }

}