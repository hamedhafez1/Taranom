package ir.roela.taranom.remote

import retrofit2.Call

class CodebazanRetroHelper {
    fun getRandomStory(): Call<String> {
        val apiService = RetroClass.getApiService()
        return apiService.getStory()
    }

    fun getRandomKnowledge(): Call<String> {
        val apiService = RetroClass.getApiService()
        return apiService.getKnowledge()
    }
}