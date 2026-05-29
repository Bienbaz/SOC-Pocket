package com.example.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.LabEntity
import com.example.data.SocRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class LabsViewModel(private val repository: SocRepository) : ViewModel() {

    val allLabs: StateFlow<List<LabEntity>> = repository.allLabs
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    init {
        viewModelScope.launch {
            repository.initializeDefaultLabs()
        }
    }

    fun completeLab(id: String) {
        viewModelScope.launch {
            repository.markLabDone(id)
        }
    }
}
