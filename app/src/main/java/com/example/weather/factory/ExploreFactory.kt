package com.example.weather.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weather.dataclass.data.viewModel.ExploreViewModel
import xyz.teamprojectx.weather.data.repository.ExploreRepository

class ExploreFactory(private val repository: ExploreRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ExploreViewModel(repository) as T
    }
}