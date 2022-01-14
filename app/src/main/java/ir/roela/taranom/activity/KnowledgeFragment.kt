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
import ir.roela.taranom.remote.RetroClass
import retrofit2.Call
import retrofit2.Response


class KnowledgeFragment : BaseFragment() {

    private lateinit var txtRandomKnowledge: TextView
    private lateinit var btnRefreshKnowledge: Button
    private lateinit var btnCopyKnowledge: Button
    private lateinit var btnShareKnowledge: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_knowledge, container, false)
        txtRandomKnowledge = view.findViewById(R.id.txtRandomKnowledge)
        btnRefreshKnowledge = view.findViewById(R.id.btnRefreshKnowledge)
        btnCopyKnowledge = view.findViewById(R.id.btnCopyKnowledge)
        btnShareKnowledge = view.findViewById(R.id.btnShareKnowledge)
        progressLoadData = view.findViewById(R.id.progressLoadData)

        txtRandomKnowledge.setOnClickListener(object : DoubleClickListener() {
            override fun onDoubleClick(v: View) {
                copyDataToClipBoard(txtRandomKnowledge.text)
            }
        })
        btnRefreshKnowledge.setOnClickListener {
            refreshKnowledge()
        }
        btnCopyKnowledge.setOnClickListener { copyDataToClipBoard(txtRandomKnowledge.text) }
        btnShareKnowledge.setOnClickListener { shareText(txtRandomKnowledge.text) }

        checkNetWorkConnection(object : Callback {
            override fun onCallback(any: Any?) {
                if (any == true)
                    refreshKnowledge()
            }
        })

        return view
    }

    private fun refreshKnowledge() {
        val apiService = RetroClass.getApiService()
        val call: Call<String> = apiService.getKnowledge()
        call.enqueue(object : retrofit2.Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (isAdded) {
                    val text = response.body().toString()
                        .replace("\\<[^>]*>", "")
                        .trim()
                    txtRandomKnowledge.text = "$text \n\n #${getString(R.string.knowledge)}"
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