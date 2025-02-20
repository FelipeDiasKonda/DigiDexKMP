package org.example.project.networking

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DigimonApiResponse(
    @SerialName("content") val digimons: List<DigimonResponse>
)

@Serializable
data class DigimonResponse(
    val id: Int,
    val name: String,
    val level: String? = null,
    val attribute: String? = null
)