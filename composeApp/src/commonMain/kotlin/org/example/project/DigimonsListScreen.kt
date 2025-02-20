import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.ktor.client.engine.cio.CIO
import org.example.project.networking.DigidexApiClient
import networking.createHttpClient
import org.example.project.viewmodel.DigidexViewModel

@Composable
fun DigimonListScreen() {
    val httpClient = remember { createHttpClient(CIO.create()) }
    val apiClient = remember { DigidexApiClient(httpClient) }
    val coroutineScope = rememberCoroutineScope()
    val viewModel = remember { DigidexViewModel(apiClient, coroutineScope) }

    LaunchedEffect(Unit) {
        viewModel.loadDigimons()
    }

    Column {
        Button(onClick = { viewModel.loadDigimons() }) {
            Text("Carregar Digimons")
        }

        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 128.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            items(viewModel.digimons.size) { index ->
                val digimon = viewModel.digimons[index]
                Card(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = digimon.name,
                            style = MaterialTheme.typography.h6
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("Nível: ${digimon.level}")
                        Text("Atributo: ${digimon.attribute}")
                    }
                }
            }
        }
    }
}