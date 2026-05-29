package com.example.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.data.SocRepository

class ViewModelFactory(private val repository: SocRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DashboardViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DashboardViewModel(repository) as T
        }
        if (modelClass.isAssignableFrom(LabsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LabsViewModel(repository) as T
        }
        if (modelClass.isAssignableFrom(AssistantViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AssistantViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
