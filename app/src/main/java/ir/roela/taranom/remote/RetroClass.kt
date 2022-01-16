package ir.roela.taranom.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class RetroClass {

    companion object {
        private const val GANJOOR_BASE_URL = "https://c.ganjoor.net"
        private const val CODEBAZAN_BASE_PATH = "https://api.codebazan.ir"

        private fun getRetroInstance(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(CODEBAZAN_BASE_PATH)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        private fun getRetroGanjoorInstance(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(GANJOOR_BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        fun getApiService(): APIService {
            return getRetroInstance().create(APIService::class.java)
        }

        fun getGanjoorApi():APIService {
            return getRetroGanjoorInstance().create(APIService::class.java)
        }


    }
}