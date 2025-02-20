package org.example.project.viewmodel

import androidx.compose.runtime.mutableStateListOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.example.project.networking.DigidexApiClient
import org.example.project.networking.DigimonResponse

class DigidexViewModel(
    private val apiClient: DigidexApiClient,
    private val coroutineScope: CoroutineScope
) {
    val digimons = mutableStateListOf<DigimonResponse>()

    fun loadDigimons() {
        coroutineScope.launch {
            val digimonsList = apiClient.getAllDigimons()
            digimons.clear()
            digimons.addAll(digimonsList)
        }
    }
}