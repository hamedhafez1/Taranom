package ir.roela.taranom.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class RetroClass {

    companion object {
        private const val BASE_PATH = "https://api.codebazan.ir"
        private fun getRetroInstance(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BASE_PATH)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        fun getApiService(): APIService {
            return getRetroInstance().create(APIService::class.java)
        }
    }
}