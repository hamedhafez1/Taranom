package ir.roela.taranom

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager

class App: Application() {

    companion object {
        const val TAG = "TARANOM_HHQ"
        fun isNetworkConnected(context: Context): Boolean {
            val cm = context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
            return cm.activeNetworkInfo != null && cm.activeNetworkInfo!!.isConnected
        }

    }
}