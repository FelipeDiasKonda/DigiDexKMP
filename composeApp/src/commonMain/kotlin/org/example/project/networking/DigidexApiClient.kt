package org.example.project.networking

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

class DigidexApiClient(
    private val httpClient: HttpClient
) {
    @Serializable
    data class DigimonApiResponse(
        @SerialName("content") val digimons: List<DigimonResponse>
    )

    suspend fun getAllDigimons(): List<DigimonResponse> {
        return try {
            val response = httpClient.get("https://digi-api.com/api/v1/digimon") {
                parameter("page", 0)
                parameter("pageSize", 1421)
            }
            response.body<DigimonApiResponse>().digimons
        } catch (e: Exception) {
            throw Exception("Falha na requisição: ${e.message}")
        }
    }
}