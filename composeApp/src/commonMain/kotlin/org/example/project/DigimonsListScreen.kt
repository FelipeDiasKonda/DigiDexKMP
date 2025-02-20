import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.ktor.client.engine.cio.CIO
import kamel.image.KamelImage
import kamel.image.asyncPainterResource
import networking.DigidexApiClient
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
                Column(
                    modifier = Modifier.padding(8.dp)
                ) {
                    val painter = asyncPainterResource(digimon.image)
                    KamelImage(
                        resource = painter,
                        contentDescription = digimon.name,
                        modifier = Modifier.size(128.dp)
                    )
                    Text(digimon.name)
                }
            }
        }
    }
}
