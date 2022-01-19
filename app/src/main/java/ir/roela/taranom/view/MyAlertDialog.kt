package ir.roela.taranom.view

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import ir.roela.taranom.BuildConfig
import ir.roela.taranom.R

class MyAlertDialog(context: Context) : AlertDialog(context) {

    fun showAboutUsDialog() {
        Builder(context)
            .setTitle(R.string.about_us)
            .setView(LayoutInflater.from(context).inflate(R.layout.about_us, null))
            .setMessage(
                context.getString(R.string.version, "تَرَنُم نسخه", " : ", BuildConfig.VERSION_NAME)
            )
            .setNegativeButton(R.string.close) { _, _ -> dismiss() }
            .show()
    }

    fun showHelpDialog() {
        Builder(context)
            .setTitle(R.string.help)
            .setView(LayoutInflater.from(context).inflate(R.layout.help, null))
            .setNegativeButton(R.string.close) { _, _ -> dismiss() }
            .show()
    }
}