package ir.roela.taranom.remote

import ir.roela.taranom.model.Poetry
import retrofit2.Call
import retrofit2.http.GET

interface APIService {
    @GET("/beyt-json.php/")
    fun getRandomPoet(): Call<Poetry>

    @GET("/dastan/")
    fun getStory(): Call<String>

    @GET("/danestani/")
    fun getKnowledge(): Call<String>
}