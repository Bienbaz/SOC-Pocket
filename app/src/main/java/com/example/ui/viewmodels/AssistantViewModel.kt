package com.example.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.network.Content
import com.example.network.GenerateContentRequest
import com.example.network.Part
import com.example.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class Message(val role: String, val text: String)

class AssistantViewModel : ViewModel() {
    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val messages: StateFlow<List<Message>> = _messages

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        _messages.update { 
            listOf(Message("model", "Hello Agent. I am your SOC AI Assistant. How can I help you analyze logs or identify threats today?"))
        }
    }

    fun sendMessage(text: String) {
        if (text.isBlank()) return
        
        _messages.update { current -> current + Message("user", text) }
        _isLoading.value = true

        viewModelScope.launch {
            try {
                // Build history
                val history = _messages.value.map { msg ->
                    Content(parts = listOf(Part(msg.text)))
                }

                val request = GenerateContentRequest(
                    contents = history,
                    systemInstruction = Content(parts = listOf(Part("You are an expert Cybersecurity AI Assistant working in a SOC. Keep answers concise, technical, and actionable.")))
                )

                val response = RetrofitClient.service.generateContent(request = request)
                val reply = response.candidates.firstOrNull()?.content?.parts?.firstOrNull()?.text ?: "No response from AI."
                
                _messages.update { current -> current + Message("model", reply) }
            } catch (e: Exception) {
                _messages.update { current -> current + Message("model", "Error analyzing request: ${e.message}") }
            } finally {
                _isLoading.value = false
            }
        }
    }
}
