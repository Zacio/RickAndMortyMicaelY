package fr.epsi.younsi.rickandmorty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import fr.epsi.younsi.rickandmorty.api.network
import fr.epsi.younsi.rickandmorty.model.Character
import fr.epsi.younsi.rickandmorty.ui.theme.RickandmortyTheme
import kotlinx.coroutines.launch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.Alignment
import coil.compose.rememberImagePainter

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RickandmortyTheme {
                CharactersScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharactersScreen() {
    var characters by remember { mutableStateOf<List<Character>?>(null) }
    var selectedCharacter by remember { mutableStateOf<Character?>(null) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        scope.launch {
            try {
                val response = network.api.getCharacters()
                characters = response.results
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Si un personnage est sélectionné, afficher l'écran des détails
    selectedCharacter?.let { character ->
        CharacterDetailsScreen(
            character = character,
            onBackClick = { selectedCharacter = null } // Revenir à l'écran de la liste
        )
    } ?: run {
        // Sinon, afficher l'écran de la liste
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Rick and Morty App") }
                )
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                if (characters == null) {
                    Text(
                        text = "Loading...",
                        modifier = Modifier.align(Alignment.Center)
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp)
                    ) {
                        items(characters!!) { character ->
                            CharacterItem(
                                character = character,
                                onClick = { selectedCharacter = character }
                            )
                        }
                    }
                }
            }
        }
    }
}



@Composable
fun CharacterItem(character: Character, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onClick) // Ajout de l'action au clic
    ) {
        Image(
            painter = rememberImagePainter(data = character.image),
            contentDescription = character.name,
            modifier = Modifier.size(64.dp),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(
                text = character.name,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Status: ${character.status}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Species: ${character.species}",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterDetailsScreen(
    character: Character,
    onBackClick: () -> Unit // Callback pour gérer le bouton retour
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(character.name) }, // Nom du personnage comme titre
                navigationIcon = {
                    IconButton(onClick = onBackClick) { // Bouton retour
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Image
            Image(
                painter = rememberImagePainter(data = character.image),
                contentDescription = character.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )
            // Statut
            Text(
                text = "Status: ${character.status}",
                style = MaterialTheme.typography.bodyLarge
            )
            // Espèce
            Text(
                text = "Species: ${character.species}",
                style = MaterialTheme.typography.bodyLarge
            )
            // Genre
            Text(
                text = "Gender: ${character.gender}",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

