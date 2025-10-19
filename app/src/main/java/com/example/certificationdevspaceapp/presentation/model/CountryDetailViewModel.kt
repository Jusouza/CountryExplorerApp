package com.example.certificationdevspaceapp.presentation.model

import androidx.lifecycle.ViewModel
import com.example.certificationdevspaceapp.domain.usecase.GetCountryByCodeUseCase

class CountryDetailViewModel(
    private val getCountryByCodeUseCase: GetCountryByCodeUseCase
): ViewModel() {


}