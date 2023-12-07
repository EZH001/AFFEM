package com.example.affem

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.affem.databinding.EquipListBinding

class MyAdapter : RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    var items: List<FileViewModel> = emptyList()
    private var onClickItem:((FileViewModel)->Unit)? = null

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = EquipListBinding.bind(itemView)
        fun bind(fileViewModel: FileViewModel){
            binding.equipIdL.text = fileViewModel.id.toString()
            binding.TitleList.text = fileViewModel.title
            binding.checkBoxList1.text = fileViewModel.malf1
            if (fileViewModel.malf2 == null)   binding.checkBoxList2.visibility = View.GONE
            else binding.checkBoxList2.text = fileViewModel.malf2
            if (fileViewModel.malf3 == null)   binding.checkBoxList3.visibility = View.GONE
            else binding.checkBoxList3.text = fileViewModel.malf3
            if (fileViewModel.malf4 == null)   binding.checkBoxList4.visibility = View.GONE
            else binding.checkBoxList4.text = fileViewModel.malf4
            if (fileViewModel.malf5 == null)   binding.checkBoxList5.visibility = View.GONE
            else binding.checkBoxList5.text = fileViewModel.malf5
            if (fileViewModel.malf6 == null)   binding.checkBoxList6.visibility = View.GONE
            else binding.checkBoxList6.text = fileViewModel.malf6
            if (fileViewModel.malf2 == "")   binding.checkBoxList2.visibility = View.GONE
            else binding.checkBoxList2.text = fileViewModel.malf2
            if (fileViewModel.malf3 == "")   binding.checkBoxList3.visibility = View.GONE
            else binding.checkBoxList3.text = fileViewModel.malf3
            if (fileViewModel.malf4 == "")   binding.checkBoxList4.visibility = View.GONE
            else binding.checkBoxList4.text = fileViewModel.malf4
            if (fileViewModel.malf5 == "")   binding.checkBoxList5.visibility = View.GONE
            else binding.checkBoxList5.text = fileViewModel.malf5
            if (fileViewModel.malf6 == "")   binding.checkBoxList6.visibility = View.GONE
            else binding.checkBoxList6.text = fileViewModel.malf6
            binding.enableBtn.setOnClickListener {
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
            }

        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = items[position]
        holder.bind(currentItem)
        holder.itemView.setOnClickListener {
            onClickItem?.invoke(currentItem)
        }

    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.equip_list, parent, false))
}
