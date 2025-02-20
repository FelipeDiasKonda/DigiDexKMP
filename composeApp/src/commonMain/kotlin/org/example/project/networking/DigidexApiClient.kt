package networking

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.serialization.SerializationException
import org.example.project.networking.DigimonResponse

class DigidexApiClient(
    private val httpClient: HttpClient
) {

    suspend fun getAllDigimons(
        name: String? = null,
        exact: Boolean = false,
        attribute: String? = null,
        page: Int = 1,
        pageSize: Int = 1400
    ): List<DigimonResponse> {
        return try {
            val response = httpClient.get(
                urlString = "https://digi-api.com/api/v1/digimon"
            ) {
                name?.let { parameter("name", it) }
                if (exact) parameter("exact", "true")
                attribute?.let { parameter("attribute", it) }
                parameter("page", page)
                parameter("pageSize", pageSize)
            }
            response.body<List<DigimonResponse>>()
        } catch (e: UnresolvedAddressException) {
            throw Exception("Sem conexão com a internet")
        } catch (e: SerializationException) {
            throw Exception("Erro ao processar os dados")
        } catch (e: Exception) {
            throw Exception("Erro desconhecido: ${e.message}")
        }
    }
}