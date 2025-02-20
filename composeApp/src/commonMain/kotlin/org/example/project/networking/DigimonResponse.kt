package org.example.project.networking

import kotlinx.serialization.Serializable

@Serializable
data class DigimonResponse(
    val id: Int,
    val name: String,
    val level: String,
    val attribute: String,
    val image: String
)