package ir.roela.taranom.activity

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.util.Log
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

abstract class BaseFragment : Fragment() {

    protected var progressLoadData: ContentLoadingProgressBar? = null

    protected fun checkNetWorkConnection(callback: Callback) {
//        if (App.isNetworkConnected(requireContext())) {
//            callback.onCallback(true)
//        } else {
//            showSnackBar(R.string.no_connection)
        ConnectivityStateMonitor(object : Callback {
            override fun onCallback(any: Any?) {
                if (any == true) {
                    callback.onCallback(true)
                } else {
                    showSnackBar(R.string.no_connection)
                }
            }
        }).enable(requireContext())
//        }
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
            val clipboard: ClipboardManager =
                requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("Text Copied!", text.trim().toString())
            clipboard.setPrimaryClip(clip)
            showSnackBar(R.string.text_copied)
        }
    }

    protected fun shareText(text: CharSequence) {
        if (text.length > 4) {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT, text.trim().toString())
            startActivity(Intent.createChooser(intent, "اشتراک گذاری با"))
        }
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
                Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Log.e(App.TAG, e.message.toString())
            Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
        }
    }
}