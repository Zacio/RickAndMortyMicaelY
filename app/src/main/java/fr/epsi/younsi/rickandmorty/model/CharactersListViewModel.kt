package fr.epsi.younsi.rickandmorty.model

data class Character(
    val id: Int,                  // Identifiant du personnage
    val name: String,             // Nom du personnage
    val status: String,           // Statut du personnage (Alive, Dead, unknown)
    val species: String,          // Espèce du personnage
    val type: String,             // Type ou sous-espèce (peut être vide)
    val gender: String,           // Genre du personnage (Male, Female, etc.)
    val origin: Location,         // Origine du personnage
    val location: Location,       // Dernière localisation connue
    val image: String,            // Lien vers l'image du personnage
    val episode: List<String>,    // Liste des épisodes où le personnage apparaît
    val url: String,              // Lien vers les détails du personnage
    val created: String           // Date de création du personnage dans la base de données
)

data class Location(
    val name: String,             // Nom de la localisation
    val url: String               // URL de la localisation
)

data class CharacterResponse(
    val results: List<Character> // Liste des personnages
)