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
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.messaging.FirebaseMessaging
import ir.roela.taranom.App
import ir.roela.taranom.App.Companion.TAG
import ir.roela.taranom.R
import ir.roela.taranom.callback.Callback
import ir.roela.taranom.databinding.ActivityMainBinding
import ir.roela.taranom.utils.Preference
import ir.roela.taranom.view.MyAlertDialog
import java.util.*


class MainActivity : ParentActivity() {

    private var mFirebaseAnalytics: FirebaseAnalytics? = null
//    private lateinit var uiModeManager: UiModeManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        resources.configuration.setLocale(Locale("fa", "IR"))
        window.decorView.layoutDirection = View.LAYOUT_DIRECTION_RTL
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this)

//        uiModeManager = getSystemService(UI_MODE_SERVICE) as UiModeManager
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
            Log.e(TAG, e.message.toString())
        }

//        getFirebaseToken()
//        viewBinding.btnSwitch.setOnClickListener {
//            when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
//                Configuration.UI_MODE_NIGHT_YES ->
//                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
//                Configuration.UI_MODE_NIGHT_NO ->
//                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
//            }
//        }
    }

//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.main_menu, menu)
//        return super.onCreateOptionsMenu(menu)
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when (item.itemId) {
//            R.id.menu_help -> {
//                showHelp()
//            }
//            R.id.menu_about_us -> {
//                showAboutUs()
//            }
//            R.id.menu_setting -> {
//                showSetting()
//            }
//        }
//        return super.onOptionsItemSelected(item)
//    }



    private fun getFirebaseToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }
            val token = task.result
            Log.i(TAG, token)
        })
    }

}