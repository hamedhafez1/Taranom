package ir.roela.taranom.view

import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ir.roela.taranom.databinding.PoemListItemBinding

class PoemViewHolder(binding: PoemListItemBinding) : RecyclerView.ViewHolder(binding.root) {

    val txtRandomPoetry: TextView = binding.txtRandomPoetry
    val txtPoetryLink: TextView = binding.txtPoetryLink
    val btnCopyPoetry: ImageButton = binding.btnCopyPoetry
    val btnSharePoetry: ImageButton = binding.btnSharePoetry

}