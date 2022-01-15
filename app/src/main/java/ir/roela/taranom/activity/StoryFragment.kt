package ir.roela.taranom.activity

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import ir.roela.taranom.App
import ir.roela.taranom.R
import ir.roela.taranom.callback.Callback
import ir.roela.taranom.callback.DoubleClickListener
import ir.roela.taranom.databinding.FragmentStoryBinding
import ir.roela.taranom.remote.RetroClass
import retrofit2.Call
import retrofit2.Response


class StoryFragment : BaseFragment() {
    private lateinit var txtRandomStory: TextView
    private lateinit var btnRefreshStory: Button
    private lateinit var btnCopyStory: Button
    private lateinit var btnShareStory: Button


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = FragmentStoryBinding.inflate(inflater)
        txtRandomStory = view.txtRandomStory
        btnRefreshStory = view.btnRefreshStory
        btnCopyStory = view.btnCopyStory
        btnShareStory = view.btnShareStory
        progressLoadData = view.progressLoadData

        txtRandomStory.setOnClickListener(object : DoubleClickListener() {
            override fun onDoubleClick(v: View) {
                copyDataToClipBoard(txtRandomStory.text)
            }
        })
        btnRefreshStory.setOnClickListener { getRandomStory() }
        btnCopyStory.setOnClickListener {
            copyDataToClipBoard(
                txtRandomStory.text
            )
        }
        btnShareStory.setOnClickListener { shareText(txtRandomStory.text) }

        checkNetWorkConnection(object : Callback {
            override fun onCallback(any: Any?) {
                if (any == true)
                    getRandomStory()
            }
        })
        return view.root
    }

    private fun getRandomStory() {
        val apiService = RetroClass.getApiService()
        val call: Call<String> = apiService.getStory()
        call.enqueue(object : retrofit2.Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (isAdded) {
                    val text = response.body().toString()
                        .replace("\\<[^>]*>", "")
                        .trim()
                    txtRandomStory.text = "$text \n\n #${getString(R.string.short_story)}"
                }
                showLoading(false)
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e(App.TAG, t.message.toString())
                showLoading(false)
            }

        })
        showLoading(true)
    }

}