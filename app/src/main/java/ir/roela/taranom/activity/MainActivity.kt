package ir.roela.taranom.activity

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import ir.roela.taranom.App
import ir.roela.taranom.R
import ir.roela.taranom.databinding.ActivityMainBinding
import com.google.firebase.analytics.FirebaseAnalytics
import ir.roela.taranom.view.MyAlertDialog
import java.util.*


class MainActivity : ParentActivity() {

    private var mFirebaseAnalytics: FirebaseAnalytics? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        resources.configuration.setLocale(Locale("fa", "IR"))
        window.decorView.layoutDirection = View.LAYOUT_DIRECTION_RTL
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this)
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_help -> {
                showHelp()
            }
            R.id.menu_about_us -> {
                showAboutUs()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showHelp() {
        MyAlertDialog(this).showHelpDialog()
    }

    private fun showAboutUs() {
        MyAlertDialog(this).showAboutUsDialog()
    }

}