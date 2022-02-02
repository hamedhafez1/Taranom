package ir.roela.taranom

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import ir.roela.taranom.utils.Preference

class App : Application() {

    companion object {
        const val TAG = "TARANOM_HHQ"
        lateinit var prefs: Preference
        fun isNetworkConnected(context: Context): Boolean {
            val cm = context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
            return cm.activeNetworkInfo != null && cm.activeNetworkInfo!!.isConnected
        }
    }

    override fun onCreate() {
        super.onCreate()
        prefs = Preference(this)
    }
}