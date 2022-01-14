package ir.roela.taranom.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import ir.roela.taranom.App
import ir.roela.taranom.callback.Callback
import ir.roela.taranom.callback.DoubleClickListener
import ir.roela.taranom.databinding.FragmentPoetryBinding
import ir.roela.taranom.model.Poetry

class PoetryFragment : BaseFragment() {

    private lateinit var txtRandomPoetry: TextView
    private lateinit var txtPoetryLink: TextView
    private lateinit var btnRefreshData: Button
    private lateinit var btnCopyPoetry: Button
    private lateinit var btnSharePoetry: Button
    private var poetryLink = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = FragmentPoetryBinding.inflate(inflater)
        txtRandomPoetry = view.txtRandomPoetry
        txtPoetryLink = view.txtPoetryLink
        btnRefreshData = view.btnRefreshData
        btnCopyPoetry = view.btnCopyPoetry
        btnSharePoetry = view.btnSharePoetry
        progressLoadData = view.progressLoadData

        txtRandomPoetry.setOnClickListener(object : DoubleClickListener() {
            override fun onDoubleClick(v: View) {
                copyDataToClipBoard(txtRandomPoetry.text)
            }
        })
        btnRefreshData.setOnClickListener { getRandomPoet() }
        btnCopyPoetry.setOnClickListener { copyDataToClipBoard(txtRandomPoetry.text) }
        btnSharePoetry.setOnClickListener { shareText(txtRandomPoetry.text) }
        txtPoetryLink.setOnClickListener { openTextLink() }

        checkNetWorkConnection(object : Callback {
            override fun onCallback(any: Any?) {
                if (any == true)
                    getRandomPoet()
            }
        })

        return view.root
    }

    private fun getRandomPoet() {
        try {
            val url = "https://c.ganjoor.net/beyt-json.php"
            val queue = Volley.newRequestQueue(requireContext())
            val request = JsonObjectRequest(Request.Method.GET, url, null, {
                if (isAdded) {
                    val poetry = Gson().fromJson(it.toString(), Poetry::class.java)
                    txtRandomPoetry.text =
                        "${poetry.bit_1}\n${poetry.bit_2}\n\n#${poetHashtag(poetry.poet)}"
                    txtPoetryLink.visibility = View.VISIBLE
                    poetryLink = poetry.url
                }
                showLoading(false)
            }, {
                Log.e(App.TAG, it.message.toString())
                txtPoetryLink.visibility = View.INVISIBLE
                showLoading(false)
            })
            queue.add(request)
            showLoading(true)
        } catch (e: Exception) {
            Log.e(App.TAG, e.message.toString())
            showLoading(false)
        }
    }

//    private fun getRandomPoets() {
//        try {
//            val url = "https://c.ganjoor.net/beyt-xml.php?n=10&a=1&p=2"
//            val queue = Volley.newRequestQueue(requireContext())
//            val request = StringRequest(Request.Method.GET, url, {
//                val jsonObject = XML.toJSONObject(it)
//                val jsonArray = jsonObject.getJSONObject("ganjoor").getJSONArray("poem")
//                val poets = Gson().fromJson(jsonArray.toString(), Array<Poetry>::class.java)
//                Log.i(App.TAG, poets.toString())
//                Log.i(App.TAG, poets[0].toString())
//            }, {
//                Log.e(App.TAG, it.toString())
//            })
//            queue.add(request)
//            showLoading(true)
//        } catch (e: Exception) {
//            Log.e(App.TAG, e.message.toString())
//            showLoading(false)
//        }
//    }

    private fun poetHashtag(poet: String): String {
        return poet.trim().replace(" ", "_")
    }

    private fun openTextLink() {
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(poetryLink)
        startActivity(i)
    }


}