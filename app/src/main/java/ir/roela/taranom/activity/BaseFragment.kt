package ir.roela.taranom.activity

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import ir.roela.taranom.App
import ir.roela.taranom.R
import ir.roela.taranom.callback.Callback
import ir.roela.taranom.callback.ConnectivityStateMonitor
import ir.roela.taranom.callback.SettingCallback
import ir.roela.taranom.utils.Preference
import ir.roela.taranom.view.MyAlertDialog

abstract class BaseFragment : Fragment() {

    protected var progressLoadData: ContentLoadingProgressBar? = null

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_help -> {
                showHelp()
            }
            R.id.menu_about_us -> {
                showAboutUs()
            }
            R.id.menu_setting -> {
                showSetting()
            }
        }
        return true
    }

    protected fun checkNetWorkConnection(callback: Callback) {
        ConnectivityStateMonitor(object : Callback {
            override fun onCallback(any: Any?) {
                if (any == true) {
                    callback.onCallback(true)
                } else {
                    showSnackBar(R.string.no_connection)
                }
            }
        }).enable(requireContext())
    }

    protected fun showLoading(state: Boolean) {
        if (isAdded)
            activity?.runOnUiThread {
                if (state) {
                    progressLoadData?.visibility = View.VISIBLE
                } else {
                    Handler(Looper.getMainLooper()).postDelayed(
                        { progressLoadData?.visibility = View.GONE },
                        500
                    )
                }
            }
    }

    protected fun copyDataToClipBoard(text: CharSequence) {
        if (text.length > 4) {
            val shareText = createContentForShare(text)
            val clipboard: ClipboardManager =
                requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("Text Copied!", shareText)
            clipboard.setPrimaryClip(clip)
            showSnackBar(R.string.text_copied)
        }
    }

    protected fun shareText(text: CharSequence) {
        if (text.length > 4) {
            val shareText = createContentForShare(text)
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT, shareText)
            startActivity(Intent.createChooser(intent, "اشتراک گذاری با"))
        }
    }

    private fun createContentForShare(text: CharSequence): String {
        val stringBuilder = StringBuilder()
        stringBuilder.append(text.trim().toString())
        stringBuilder.append("\n\n")
        stringBuilder.append(getSubContentDesc())
        return stringBuilder.toString()
    }

    private fun getSubContentDesc(): String {
        val desc = App.prefs.getStringPreference(Preference.PType.SubContentDesc)
        return if (desc !== null) {
            desc
        } else ""
    }

    protected fun showSnackBar(text: Int) {
        try {
            if (isAdded) {
                val snackBar = Snackbar.make(
                    requireActivity().findViewById(android.R.id.content),
                    text,
                    Snackbar.LENGTH_SHORT
                )
                val snackBarView = snackBar.view
                snackBarView.layoutDirection = View.LAYOUT_DIRECTION_RTL
                val textView = snackBarView.findViewById<TextView>(R.id.snackbar_text)
                textView.textSize = 14F
                snackBar.show()
            } else {
                Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Log.e(App.TAG, e.message.toString())
            Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show()
        }
    }

    private fun showHelp() {
        MyAlertDialog(requireContext()).showHelpDialog()
    }

    private fun showAboutUs() {
        MyAlertDialog(requireContext()).showAboutUsDialog()
    }

    private fun showSetting() {
        MyAlertDialog(requireContext()).showSettingDialog(object : SettingCallback {
            override fun onSubDescChange() {
                showSnackBar(R.string.saved)
            }
        })
    }

}