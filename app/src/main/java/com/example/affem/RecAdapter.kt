package com.example.affem

import android.content.Context
import android.view.LayoutInflater
import android.view.ScrollCaptureCallback
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.affem.databinding.EquipListBinding

class RecAdapter: RecyclerView.Adapter<RecAdapter.ViewHolder>() {
    var itemsList = ArrayList<ItemsViewModel>()
    private var onClickItem:((ItemsViewModel)->Unit)? = null
    fun addItems(items: ArrayList<ItemsViewModel>){
        itemsList = items
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.equip_list, parent, false))



    override fun onBindViewHolder(holder: RecAdapter.ViewHolder, position: Int) {
        val items = itemsList[position]
        holder.bind(items)
        holder.itemView.setOnClickListener {
            onClickItem?.invoke(items)
        }
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

    class ViewHolder(item: View): RecyclerView.ViewHolder(item) {
        val binding = EquipListBinding.bind(item)
        fun bind(itemsViewModel: ItemsViewModel){
            binding.equipIdL.text = itemsViewModel.id.toString()
            binding.TitleList.text = itemsViewModel.title.toString()
        }
    }
}