package fr.epsi.younsi.rickandmorty.api

import fr.epsi.younsi.rickandmorty.model.CharacterResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

object network {
    private const val BASE_URL = "https://rickandmortyapi.com/api/"

    interface RickAndMortyApi {
        @GET("character")
        suspend fun getCharacters(): CharacterResponse // Retourne un CharacterResponse, pas un Character
    }
    // Instance Retrofit
    val api: RickAndMortyApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RickAndMortyApi::class.java)
    }
}
