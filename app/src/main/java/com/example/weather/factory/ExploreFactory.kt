package xyz.teamprojectx.weather.data.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import xyz.teamprojectx.weather.data.repository.ExploreRepository
import xyz.teamprojectx.weather.data.viewModel.ExploreViewModel

class ExploreFactory(private val repository: ExploreRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ExploreViewModel(repository) as T
    }
}