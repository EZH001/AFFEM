package com.example.affem

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.affem.databinding.EquipListBinding
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class RecAdapter(private val context: Context): RecyclerView.Adapter<RecAdapter.ViewHolder>() {
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

        holder.binding.checkBoxList1.isChecked = items.isChecked1
        holder.binding.checkBoxList1.setOnCheckedChangeListener { _, isChecked ->
            items.updateCheckBoxStatus(1, isChecked)
        }
        holder.binding.checkBoxList2.isChecked = items.isChecked2
        holder.binding.checkBoxList2.setOnCheckedChangeListener { _, isChecked ->
            items.updateCheckBoxStatus(2, isChecked)
        }
        holder.binding.checkBoxList3.isChecked = items.isChecked3
        holder.binding.checkBoxList3.setOnCheckedChangeListener { _, isChecked ->
            items.updateCheckBoxStatus(3, isChecked)
        }
        holder.binding.checkBoxList4.isChecked = items.isChecked4
        holder.binding.checkBoxList4.setOnCheckedChangeListener { _, isChecked ->
            items.updateCheckBoxStatus(4, isChecked)
        }
        holder.binding.checkBoxList5.isChecked = items.isChecked5
        holder.binding.checkBoxList5.setOnCheckedChangeListener { _, isChecked ->
            items.updateCheckBoxStatus(5, isChecked)
        }
        holder.binding.checkBoxList6.isChecked = items.isChecked6
        holder.binding.checkBoxList6.setOnCheckedChangeListener { _, isChecked ->
            items.updateCheckBoxStatus(6, isChecked)
        }

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
            if (itemsViewModel.malf2 == null || itemsViewModel.malf2 == "" || itemsViewModel.malf2 == "null") binding.checkBoxList2.visibility = View.GONE
            else binding.checkBoxList2.text = itemsViewModel.malf2
            if (itemsViewModel.malf3 == null || itemsViewModel.malf3 == "" || itemsViewModel.malf3 == "null") binding.checkBoxList3.visibility = View.GONE
            else binding.checkBoxList3.text = itemsViewModel.malf3
            if (itemsViewModel.malf4 == null || itemsViewModel.malf4 == "" || itemsViewModel.malf4 == "null") binding.checkBoxList4.visibility = View.GONE
            else binding.checkBoxList4.text = itemsViewModel.malf4
            if (itemsViewModel.malf5 == null || itemsViewModel.malf5 == "" || itemsViewModel.malf5.trim() == "null") binding.checkBoxList5.visibility = View.GONE
            else binding.checkBoxList5.text = itemsViewModel.malf5
            if (itemsViewModel.malf6 == null || itemsViewModel.malf6 == "" || itemsViewModel.malf6 == "null") binding.checkBoxList6.visibility = View.GONE
            else binding.checkBoxList6.text = itemsViewModel.malf6
            binding.checkBoxList1.isChecked = itemsViewModel.isChecked1
            binding.checkBoxList2.isChecked = itemsViewModel.isChecked2
            binding.checkBoxList3.isChecked = itemsViewModel.isChecked3
            binding.checkBoxList4.isChecked = itemsViewModel.isChecked4
            binding.checkBoxList5.isChecked = itemsViewModel.isChecked5
            binding.checkBoxList5.isChecked = itemsViewModel.isChecked6


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
    @RequiresApi(Build.VERSION_CODES.O)
    fun saveDataToTextFile(context: Context, dataList: ArrayList<ItemsViewModel>) {
        val fileName = getCurrentDate()
        try {
            val file = File(context.filesDir, "$fileName.txt")
            val writer = FileWriter(file)
            val bufferedWriter = BufferedWriter(writer)

            for (data in dataList) {
                val line = "${data.id}, ${data.title}, ${data.malf1}, ${if (data.isChecked1) "true" else "false"}," +
                        "${data.malf2}, ${if (data.isChecked2) "true" else "false"}, ${data.malf3}, ${if (data.isChecked3) "true" else "false"}," +
                        "${data.malf4}, ${if (data.isChecked4) "true" else "false"}, ${data.malf5}, ${if (data.isChecked5) "true" else "false"}," +
                        "${data.malf6}, ${if (data.isChecked6) "true" else "false"}"
                bufferedWriter.write(line)
                bufferedWriter.newLine()
            }

            bufferedWriter.close()
            writer.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun loadDataFromFile(context: Context, filePath: String): ArrayList<ItemsViewModel> {
        val file = File(context.filesDir.absolutePath, filePath)
        val loadedDataList = ArrayList<ItemsViewModel>()

        try {
            val lines = file.readLines()

            for (line in lines) {
                val values = line.split(",")

                // Получение значений состояний чекбоксов
                val isChecked1 = values[3].trim() == "true"
                val isChecked2 = values[5].trim() == "true"
                val isChecked3 = values[7].trim() == "true"
                val isChecked4 = values[9].trim() == "true"
                val isChecked5 = values[11].trim() == "true"
                val isChecked6 = values[13].trim() == "true"

                // Создание объекта ItemsViewModel с учетом состояний чекбоксов
                val item = ItemsViewModel(
                    values[0].toInt(),
                    values[1],
                    values[2],
                    values[3],
                    values[4],
                    values[5],
                    values[6],
                    values[7],
                    values[8],
                    values[9],
                    values[10],
                    values[11],
                    values[12],
                    values[13],
                    isChecked1,
                    isChecked2,
                    isChecked3,
                    isChecked4,
                    isChecked5,
                    isChecked6
                )
                loadedDataList.add(item)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return loadedDataList
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun getCurrentDate(): String {
        val currentDate = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yy")
        return currentDate.format(formatter)
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