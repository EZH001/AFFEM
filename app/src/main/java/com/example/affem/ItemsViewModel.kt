package com.example.affem

data class ItemsViewModel(val id: Int, val title:String, val malf1: String, val status1: String = "false", val malf2: String?,
                          val status2: String = "false", val malf3: String?, val status3: String = "false", val malf4: String?,
                          val status4: String = "false", val malf5: String?, val status5: String = "false", val malf6: String?, val status6: String = "false",
                          var isChecked1: Boolean = false, // Добавлены булевые свойства для чекбоксов
                          var isChecked2: Boolean = false,
                          var isChecked3: Boolean = false,
                          var isChecked4: Boolean = false,
                          var isChecked5: Boolean = false,
                          var isChecked6: Boolean = false)
{
    fun updateCheckBoxStatus(position: Int, isChecked: Boolean) {
        when (position) {
            1 -> isChecked1 = isChecked
            2 -> isChecked2 = isChecked
            3 -> isChecked3 = isChecked
            4 -> isChecked4 = isChecked
            5 -> isChecked5 = isChecked
            6 -> isChecked6 = isChecked
            // Add more cases if needed
        }
    }
}
