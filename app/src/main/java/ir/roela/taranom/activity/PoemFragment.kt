package ir.roela.taranom.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ir.roela.taranom.App
import ir.roela.taranom.R
import ir.roela.taranom.adapter.RandomPoemsAdapter
import ir.roela.taranom.callback.Callback
import ir.roela.taranom.callback.PageInit
import ir.roela.taranom.databinding.FragmentPoemBinding
import ir.roela.taranom.model.Poem
import ir.roela.taranom.remote.GanjoorRetroHelper
import org.json.XML
import retrofit2.Call
import retrofit2.Response
import java.lang.reflect.Type


class PoemFragment : BaseFragment(), RandomPoemsAdapter.Callback, PageInit {

    private lateinit var lytConnecting: LinearLayout
    private lateinit var poemRecyclerView: RecyclerView
    private lateinit var randomPoemsAdapter: RandomPoemsAdapter
    private lateinit var swipePoemsRefresh: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        val view = FragmentPoemBinding.inflate(inflater)
        lytConnecting = view.lytConnecting
        swipePoemsRefresh = view.swipePoemsRefresh
        poemRecyclerView = view.listViewPoem
        randomPoemsAdapter = RandomPoemsAdapter(this)
        swipePoemsRefresh.setColorSchemeResources(R.color.purple_200)

        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        poemRecyclerView.layoutManager = linearLayoutManager
        poemRecyclerView.itemAnimator = DefaultItemAnimator()
        poemRecyclerView.adapter = randomPoemsAdapter
        val itemDecorator = DividerItemDecoration(context, linearLayoutManager.orientation)
        ContextCompat.getDrawable(requireContext(), R.drawable.divider)?.let { drawable ->
            itemDecorator.setDrawable(drawable)
        }
        poemRecyclerView.addItemDecoration(itemDecorator)
        randomPoemsAdapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            /**MORE ACTION CAN BE OVERRIDE*/
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                poemRecyclerView.scrollToPosition(0)
            }

        })

        view.fabRefreshData.setOnClickListener { refreshData() }
        swipePoemsRefresh.setOnRefreshListener { refreshData() }

        checkNetWorkConnection(object : Callback {
            override fun onCallback(any: Any?) {
                if (any == true)
                    getRandomPoets()
            }
        })

        return view.root
    }

    private fun refreshData() {
        if (App.isNetworkConnected(requireContext())) {
            getRandomPoets()
        } else {
            showSnackBar(R.string.no_connection)
            swipePoemsRefresh.isRefreshing = false
        }
    }

    override fun onContentCopy(content: String) {
        copyDataToClipBoard(content)
    }

    override fun onContentShare(content: String) {
        shareText(content)
    }

    override fun onOpenPoemUrl(url: String) {
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        startActivity(i)
    }

    private fun getRandomPoets() {
        try {
            GanjoorRetroHelper().getPoems().enqueue(object : retrofit2.Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    showRefreshLoading(false)
                    if (isAdded && response.body() !== null) {
                        val jsonObject = XML.toJSONObject(response.body())
                        val jsonArray =
                            jsonObject.getJSONObject("ganjoor").getJSONArray("poem").toString()
                        val listType: Type = object : TypeToken<List<Poem>>() {}.type
                        val poemList: List<Poem> = Gson().fromJson(jsonArray, listType)
                        requireActivity().runOnUiThread {
                            randomPoemsAdapter.setItems(poemList)
                            initLayoutNoData(poemList.size)
                        }
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    showRefreshLoading(false)
                    Log.e(App.TAG, "getRandomPoets error in api call " + t.message.toString())
                }

            })
            showRefreshLoading(true)
        } catch (e: Exception) {
            Log.e(App.TAG, e.message.toString())
            showRefreshLoading(false)
        }
    }

    private fun showRefreshLoading(show: Boolean) {
        requireActivity().runOnUiThread {
            swipePoemsRefresh.isRefreshing = show
        }
    }

    override fun initLayoutNoData(listSize: Int) {
        requireActivity().runOnUiThread {
            if (listSize == 0) {
                lytConnecting.visibility = View.VISIBLE
            } else {
                lytConnecting.visibility = View.GONE
            }
        }
    }


}