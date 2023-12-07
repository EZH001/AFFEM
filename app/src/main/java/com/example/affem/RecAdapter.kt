package com.example.affem

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.affem.databinding.EquipListBinding


class RecAdapter: RecyclerView.Adapter<RecAdapter.ViewHolder>() {
    var itemsList = ArrayList<ItemsViewModel>()
    private var onClickItem:((ItemsViewModel)->Unit)? = null
    private var isItemsEnabled = false

    fun setItemsEnabled(enabled: Boolean) {
        isItemsEnabled = enabled
        notifyDataSetChanged()
    }
    fun addItems(items: ArrayList<ItemsViewModel>){
        itemsList = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.equip_list, parent, false))

    override fun onBindViewHolder(holder: RecAdapter.ViewHolder, position: Int) {
        val items = itemsList[position]
        holder.bind(items)
        holder.enableCheckBoxes(isItemsEnabled)
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
            binding.TitleList.text = itemsViewModel.title
            binding.checkBoxList1.text = itemsViewModel.malf1
            if (itemsViewModel.malf2 == null)   binding.checkBoxList2.visibility = View.GONE
            else binding.checkBoxList2.text = itemsViewModel.malf2
            if (itemsViewModel.malf3 == null)   binding.checkBoxList3.visibility = View.GONE
            else binding.checkBoxList3.text = itemsViewModel.malf3
            if (itemsViewModel.malf4 == null)   binding.checkBoxList4.visibility = View.GONE
            else binding.checkBoxList4.text = itemsViewModel.malf4
            if (itemsViewModel.malf5 == null)   binding.checkBoxList5.visibility = View.GONE
            else binding.checkBoxList5.text = itemsViewModel.malf5
            if (itemsViewModel.malf6 == null)   binding.checkBoxList6.visibility = View.GONE
            else binding.checkBoxList6.text = itemsViewModel.malf6
            if (itemsViewModel.malf2 == "")   binding.checkBoxList2.visibility = View.GONE
            else binding.checkBoxList2.text = itemsViewModel.malf2
            if (itemsViewModel.malf3 == "")   binding.checkBoxList3.visibility = View.GONE
            else binding.checkBoxList3.text = itemsViewModel.malf3
            if (itemsViewModel.malf4 == "")   binding.checkBoxList4.visibility = View.GONE
            else binding.checkBoxList4.text = itemsViewModel.malf4
            if (itemsViewModel.malf5 == "")   binding.checkBoxList5.visibility = View.GONE
            else binding.checkBoxList5.text = itemsViewModel.malf5
            if (itemsViewModel.malf6 == "")   binding.checkBoxList6.visibility = View.GONE
            else binding.checkBoxList6.text = itemsViewModel.malf6
            binding.checkBoxList1.isChecked = itemsViewModel.status1.toBoolean()
            binding.checkBoxList2.isChecked = itemsViewModel.status2.toBoolean()
            binding.checkBoxList3.isChecked = itemsViewModel.status3.toBoolean()
            binding.checkBoxList4.isChecked = itemsViewModel.status4.toBoolean()
            binding.checkBoxList5.isChecked = itemsViewModel.status5.toBoolean()
            binding.checkBoxList5.isChecked = itemsViewModel.status6.toBoolean()

        }
        fun enableCheckBoxes(enabled: Boolean) {
            binding.checkBoxList1.isEnabled = enabled
            binding.checkBoxList2.isEnabled = enabled
            binding.checkBoxList3.isEnabled = enabled
            binding.checkBoxList4.isEnabled = enabled
            binding.checkBoxList5.isEnabled = enabled
            binding.checkBoxList6.isEnabled = enabled
            // ... включите или выключите остальные чекбоксы
        }
    }
}
/*binding.enableBtn.setOnClickListener {
                binding.checkBoxList1.isEnabled = false
                binding.checkBoxList2.isEnabled = false
                binding.checkBoxList3.isEnabled = false
                binding.checkBoxList4.isEnabled = false
                binding.checkBoxList5.isEnabled = false
                binding.checkBoxList6.isEnabled = false
            }
            binding.disBtn.setOnClickListener {
                binding.checkBoxList1.isEnabled = true
                binding.checkBoxList2.isEnabled = true
                binding.checkBoxList3.isEnabled = true
                binding.checkBoxList4.isEnabled = true
                binding.checkBoxList5.isEnabled = true
                binding.checkBoxList6.isEnabled = true
            }*/