package com.example.certificationdevspaceapp.domain.usecase

import com.example.certificationdevspaceapp.data.repository.Repository
import com.example.certificationdevspaceapp.domain.model.CountryDomain
import com.example.certificationdevspaceapp.domain.model.toDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetAllCountriesUseCase (
    private val repository: Repository
) {
    suspend operator fun invoke(): Result<List<CountryDomain>> = withContext(Dispatchers.IO) {
        try {
            val result = repository.getAllCountries().map { it.toDomain() }
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}