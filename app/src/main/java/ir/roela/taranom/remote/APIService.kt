package ir.roela.taranom.remote

import ir.roela.taranom.model.Poetry
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {
    @GET("/beyt-json.php/")
    fun getRandomPoet(): Call<Poetry>

    @GET("/beyt-xml.php?a=1")
    fun getPoem(@Query("p") poet: Int = 0, @Query("n") number: Int = 10): Call<String>

    @GET("/dastan/")
    fun getStory(): Call<String>

    @GET("/danestani/")
    fun getKnowledge(): Call<String>
}