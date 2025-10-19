package com.example.certificationdevspaceapp.domain.usecase

import com.example.certificationdevspaceapp.data.repository.Repository
import com.example.certificationdevspaceapp.domain.model.CountryDomain
import com.example.certificationdevspaceapp.domain.model.toDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetCountryByCodeUseCase(
    private val repository: Repository
) {
    suspend operator fun invoke(code: String): Result<CountryDomain> = withContext(Dispatchers.IO) {
        try {
            val dto = repository.getCountryByCode(code)
            if (dto != null) {
                Result.success(dto.toDomain())
            } else {
                Result.failure(Exception("Country not found"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}