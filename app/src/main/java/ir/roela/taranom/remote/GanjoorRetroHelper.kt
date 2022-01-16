package ir.roela.taranom.remote

import ir.roela.taranom.model.Poetry
import retrofit2.Call

class GanjoorRetroHelper {

    fun getRandomPoet(): Call<Poetry> {
        val apiService = RetroClass.getGanjoorApi()
        return apiService.getRandomPoet()
    }
}