package ir.roela.taranom.activity

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import ir.roela.taranom.App
import ir.roela.taranom.R
import ir.roela.taranom.databinding.ActivityMainBinding
import com.google.firebase.analytics.FirebaseAnalytics


class MainActivity : ParentActivity() {

    private var mFirebaseAnalytics: FirebaseAnalytics? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        val viewBinding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(viewBinding.root)
        val toolbar = viewBinding.lytBanner
        setSupportActionBar(toolbar)
        try {
            val navView = viewBinding.navigation
            val navController = findNavController(R.id.nav_host_fragment)
            val appBarConfiguration = AppBarConfiguration(
                setOf(
                    R.id.navigation_home,
                    R.id.navigation_story,
                    R.id.navigation_knowledge,
                )
            )
            setupActionBarWithNavController(navController, appBarConfiguration)
            navView.setupWithNavController(navController)
        } catch (e: Exception) {
            Log.e(App.TAG, e.message.toString())
        }

    }

}