package com.example.certificationdevspaceapp.data.network

import com.example.certificationdevspaceapp.data.model.CountryDto
import retrofit2.http.GET
import retrofit2.http.Path

import retrofit2.http.Url

interface Api {
    @GET("all?fields=name,capital,flags,region,languages,currencies,population,cca2,cca3")
    suspend fun getAllCountries(): List<CountryDto>

    @GET("alpha/{code}?fields=name,capital,flags,region,languages,currencies,population,cca2,cca3")
    suspend fun getCountryByCode(@Path("code") code: String): CountryDto

}
