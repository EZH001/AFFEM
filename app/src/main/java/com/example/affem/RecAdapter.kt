package com.example.affem

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.affem.databinding.EquipListBinding
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.IOException
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class RecAdapter(private val context: Context): RecyclerView.Adapter<RecAdapter.ViewHolder>() {
    var itemsList = ArrayList<ItemsViewModel>()
    private var onClickItem:((ItemsViewModel)->Unit)? = null
    private var isItemsEnabled = false

    @SuppressLint("NotifyDataSetChanged")
    fun setItemsEnabled(enabled: Boolean) {
        isItemsEnabled = enabled
        notifyDataSetChanged()
    }
    @SuppressLint("NotifyDataSetChanged")
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

        holder.binding.checkBoxList1.post {
            holder.binding.checkBoxList1.isChecked = items.isChecked1
        }
        holder.binding.checkBoxList1.setOnCheckedChangeListener { _, isChecked ->
            items.updateCheckBoxStatus(1, isChecked)
        }
        holder.binding.checkBoxList2.post {
            holder.binding.checkBoxList2.isChecked = items.isChecked2
        }
        holder.binding.checkBoxList2.setOnCheckedChangeListener { _, isChecked ->
            items.updateCheckBoxStatus(2, isChecked)
        }
        holder.binding.checkBoxList3.post {
            holder.binding.checkBoxList3.isChecked = items.isChecked3
        }
        holder.binding.checkBoxList3.setOnCheckedChangeListener { _, isChecked ->
            items.updateCheckBoxStatus(3, isChecked)
        }
        holder.binding.checkBoxList4.post {
            holder.binding.checkBoxList4.isChecked = items.isChecked4
        }
        holder.binding.checkBoxList4.setOnCheckedChangeListener { _, isChecked ->
            items.updateCheckBoxStatus(4, isChecked)
        }
        holder.binding.checkBoxList5.post {
            holder.binding.checkBoxList5.isChecked = items.isChecked5
        }
        holder.binding.checkBoxList5.setOnCheckedChangeListener { _, isChecked ->
            items.updateCheckBoxStatus(5, isChecked)
        }
        holder.binding.checkBoxList6.post {
            holder.binding.checkBoxList6.isChecked = items.isChecked6
        }
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
            if (itemsViewModel.malf2 == null || itemsViewModel.malf2 == "" || itemsViewModel.malf2 == "null" || itemsViewModel.malf2 == "null1") binding.checkBoxList2.visibility = View.GONE
            else binding.checkBoxList2.text = itemsViewModel.malf2
            if (itemsViewModel.malf3 == null || itemsViewModel.malf3 == "" || itemsViewModel.malf3 == "null" || itemsViewModel.malf3 == "null1") binding.checkBoxList3.visibility = View.GONE
            else binding.checkBoxList3.text = itemsViewModel.malf3
            if (itemsViewModel.malf4 == null || itemsViewModel.malf4 == "" || itemsViewModel.malf4 == "null" || itemsViewModel.malf4 == "null1") binding.checkBoxList4.visibility = View.GONE
            else binding.checkBoxList4.text = itemsViewModel.malf4
            if (itemsViewModel.malf5 == null || itemsViewModel.malf5 == "" || itemsViewModel.malf5 == "null" || itemsViewModel.malf5 == "null1") binding.checkBoxList5.visibility = View.GONE
            else binding.checkBoxList5.text = itemsViewModel.malf5
            if (itemsViewModel.malf6 == null || itemsViewModel.malf6 == "" || itemsViewModel.malf6 == "null" || itemsViewModel.malf6 == "null1") binding.checkBoxList6.visibility = View.GONE
            else binding.checkBoxList6.text = itemsViewModel.malf6
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

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    fun saveDataToTextFile(context: Context, dataList: ArrayList<ItemsViewModel>) {
        val fileName = getCurrentDate()

        try {
            BufferedWriter(
                OutputStreamWriter(
                    context.openFileOutput("$fileName.txt", Context.MODE_PRIVATE),
                    Charsets.UTF_8
                )
            ).use { bufferedWriter ->
                for (data in dataList) {
                    val line =
                        "${data.id}, ${data.title}, ${data.malf1}, ${data.isChecked1}, " +
                                "${data.malf2 ?: ""}, ${data.isChecked2}, " +
                                "${data.malf3 ?: ""}, ${data.isChecked3}, " +
                                "${data.malf4 ?: ""}, ${data.isChecked4}, " +
                                "${data.malf5 ?: ""}, ${data.isChecked5}, " +
                                "${data.malf6 ?: ""}, ${data.isChecked6}"
                    bufferedWriter.write(line)
                    bufferedWriter.newLine()
                }
                // Добавленная строка для гарантированной записи данных
                bufferedWriter.flush()
            }
        } catch (e: IOException) {
            e.printStackTrace()
            // Обработка ошибок
        }
    }

    fun loadDataFromTextFile(context: Context, fileName: String): ArrayList<ItemsViewModel> {
        val dataList = ArrayList<ItemsViewModel>()
        try {
            val inputStream = context.openFileInput("$fileName.txt")
            val reader = BufferedReader(InputStreamReader(inputStream))
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                val values = line!!.split(", ")
                val item = ItemsViewModel(
                    id = values[0].toInt(),
                    title = values[1],
                    malf1 = values[2],
                    isChecked1 = values[3].toBoolean(),
                    malf2 = values[4],
                    isChecked2 = values[5].toBoolean(),
                    malf3 = values[6],
                    isChecked3 = values[7].toBoolean(),
                    malf4 = values[8],
                    isChecked4 = values[9].toBoolean(),
                    malf5 = values[10],
                    isChecked5 = values[11].toBoolean(),
                    malf6 = values[12],
                    isChecked6 = values[13].toBoolean()
                )
                dataList.add(item)
            }
            reader.close()
        } catch (e: IOException) {
            e.printStackTrace()
            // Обработка ошибок
        }
        return dataList
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun getCurrentDate(): String {
        val currentDate = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("dd.MM.yy")
        return currentDate.format(formatter)
    }
    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newDataList: ArrayList<ItemsViewModel>) {
        itemsList = newDataList
        notifyDataSetChanged()
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