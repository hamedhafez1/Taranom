package ir.roela.taranom.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ir.roela.taranom.callback.DoubleClickListener
import ir.roela.taranom.databinding.PoemListItemBinding
import ir.roela.taranom.model.Poem
import ir.roela.taranom.utils.Utils
import ir.roela.taranom.view.PoemViewHolder


class RandomPoemsAdapter(
    private val callback: Callback
) : RecyclerView.Adapter<PoemViewHolder>() {

    private var items: List<Poem> = ArrayList()

    interface Callback {
        fun onContentCopy(content: String)
        fun onContentShare(content: String)
        fun onOpenPoemUrl(url: String)
    }

    /*companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<Poem> =
            object : DiffUtil.ItemCallback<Poem>() {
                override fun areItemsTheSame(oldItem: Poem, newItem: Poem): Boolean {
                    return false
                }

                override fun areContentsTheSame(oldItem: Poem, newItem: Poem): Boolean {
                    return false
                }
            }
    }*/

    override fun getItemCount(): Int {
        return items.size
    }

    fun setItems(items: List<Poem>) {
        this.items = items
        notifyDataSetChanged()
    }

    private fun getItem(position: Int): Poem {
        return items[position]
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PoemViewHolder {
        val listItemBinding =
            PoemListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PoemViewHolder(listItemBinding)
    }

    override fun onBindViewHolder(holder: PoemViewHolder, position: Int) {
        getItem(position).let { poem ->
            holder.txtRandomPoetry.text = Utils.fillPoem(poem)
            holder.txtPoetryLink.visibility = View.VISIBLE
            holder.txtRandomPoetry.setOnClickListener(object : DoubleClickListener() {
                override fun onDoubleClick(v: View) {
                    callback.onContentCopy(
                        holder.txtRandomPoetry.text.toString()
                    )
                }
            })
            holder.btnCopyPoetry.setOnClickListener {
                callback.onContentCopy(
                    holder.txtRandomPoetry.text.toString()
                )
            }
            holder.btnSharePoetry.setOnClickListener {
                callback.onContentShare(holder.txtRandomPoetry.text.toString())
            }
            holder.txtPoetryLink.setOnClickListener { callback.onOpenPoemUrl(poem.url) }
        }
    }


}