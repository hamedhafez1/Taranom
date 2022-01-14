package ir.roela.taranom.remote

import retrofit2.Call
import retrofit2.http.GET

interface APIService {
    @GET("/dastan/")
    fun getStory(): Call<String>
    @GET("/danestani/")
    fun getKnowledge(): Call<String>
}