package ir.roela.taranom.view

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import ir.roela.taranom.App
import ir.roela.taranom.BuildConfig
import ir.roela.taranom.R
import ir.roela.taranom.callback.SettingCallback
import ir.roela.taranom.databinding.AboutUsBinding
import ir.roela.taranom.databinding.HelpBinding
import ir.roela.taranom.databinding.SettingBinding
import ir.roela.taranom.utils.Preference

class MyAlertDialog(context: Context) : AlertDialog(context) {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    fun showAboutUsDialog() {
        Builder(context)
            .setTitle(R.string.about_us)
            .setView(AboutUsBinding.inflate(inflater).root)
            .setMessage(
                context.getString(R.string.version, "تَرَنُم نسخه", " : ", BuildConfig.VERSION_NAME)
            )
            .setNegativeButton(R.string.close) { _, _ -> dismiss() }
            .show()
    }

    fun showHelpDialog() {
        Builder(context)
            .setTitle(R.string.help)
            .setView(HelpBinding.inflate(inflater).root)
            .setNegativeButton(R.string.close) { _, _ -> dismiss() }
            .show()
    }

    fun showSettingDialog(settingCallback: SettingCallback) {
        val view = SettingBinding.inflate(inflater)
        val edtSubContentDesc = view.edtSubContentDesc
        val chkShowHashtag = view.chkShowHashtag

        val checked = App.prefs.getBooleanPreference(Preference.PType.InsertAppHashtag)

        chkShowHashtag.isChecked = checked
        chkShowHashtag.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                if (edtSubContentDesc.text?.trim().toString() != "") {
                    App.prefs.setBooleanPreference(Preference.PType.InsertAppHashtag, true)
                } else {
                    Handler(Looper.getMainLooper()).postDelayed({
                        chkShowHashtag.isChecked = false
                    }, 150)
                    Toast.makeText(context, "برای فعال کردن در قسمت زیر چیزی بنویسید", Toast.LENGTH_SHORT).show()
                }
            } else {
                App.prefs.setBooleanPreference(Preference.PType.InsertAppHashtag, false)
            }
        }
        edtSubContentDesc.setText(App.prefs.getStringPreference(Preference.PType.SubContentDesc))

        val builder = Builder(context)
            .setTitle(R.string.setting)
            .setView(view.root)
            .setNegativeButton(R.string.close) { _, _ -> dismiss() }
            .setPositiveButton(R.string.save) { _, _ ->
                val desc = edtSubContentDesc.text?.trim().toString()
                App.prefs.setStringPreference(Preference.PType.SubContentDesc, desc)
                settingCallback.onSubDescChange()
                dismiss()
            }
        val dialog = builder.create()
        dialog.show()
        dialog.getButton(BUTTON_POSITIVE).isEnabled = false
        edtSubContentDesc.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                dialog.getButton(BUTTON_POSITIVE).isEnabled = true
            }

        })
    }
}