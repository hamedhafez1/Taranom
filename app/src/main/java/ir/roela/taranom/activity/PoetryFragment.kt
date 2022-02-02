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
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ir.roela.taranom.App
import ir.roela.taranom.R
import ir.roela.taranom.callback.Callback
import ir.roela.taranom.callback.DoubleClickListener
import ir.roela.taranom.databinding.FragmentPoetryBinding
import ir.roela.taranom.model.Poem
import ir.roela.taranom.model.Poetry
import ir.roela.taranom.remote.GanjoorRetroHelper
import ir.roela.taranom.utils.Utils
import org.json.XML
import retrofit2.Call
import retrofit2.Response
import java.lang.reflect.Type

@Deprecated("")
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
        getRandomPoets()
        return view.root
    }

    private fun getRandomPoet() {
        try {
            GanjoorRetroHelper().getRandomPoet()
                .enqueue(object : retrofit2.Callback<Poetry> {
                    override fun onResponse(call: Call<Poetry>, response: Response<Poetry>) {
                        if (isAdded && response.body() !== null) {
                            val poetry = response.body() as Poetry
                            fillContent(poetry)
                        }
                        showLoading(false)
                    }

                    override fun onFailure(call: Call<Poetry>, t: Throwable) {
                        Log.e(App.TAG, "getRandomPoet error in api call " + t.message.toString())
                        showLoading(false)
                    }

                })
            showLoading(true)
        } catch (e: Exception) {
            Log.e(App.TAG, e.message.toString())
            showLoading(false)
        }
    }

    private fun fillContent(poetry: Poetry) {
        txtRandomPoetry.text = getString(
            R.string.poetry_text,
            poetry.bit_1,
            poetry.bit_2,
            Utils.poetHashtag(poetry.poet)
        )
        txtPoetryLink.visibility = View.VISIBLE
        poetryLink = poetry.url
    }

    private fun getRandomPoets() {
        try {
            GanjoorRetroHelper().getPoems()
                .enqueue(object : retrofit2.Callback<String> {
                    override fun onResponse(call: Call<String>, response: Response<String>) {
                        if (isAdded && response.body() !== null) {
                            val jsonObject = XML.toJSONObject(response.body())
                            val jsonArray =
                                jsonObject.getJSONObject("ganjoor").getJSONArray("poem").toString()
                            val listType: Type = object : TypeToken<List<Poem>>() {}.type
                            val poemList: List<Poem> = Gson().fromJson(jsonArray, listType)
                            poemList.forEach {
                                Log.i(App.TAG, it.url)
                            }
                        }
                    }

                    override fun onFailure(call: Call<String>, t: Throwable) {
                        Log.e(App.TAG, "getRandomPoets error in api call " + t.message.toString())
                    }

                })
        } catch (e: Exception) {
            Log.e(App.TAG, e.message.toString())
            showLoading(false)
        }
    }

    private fun openTextLink() {
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(poetryLink)
        startActivity(i)
    }


}