package com.purabmodi.agrihelp.ui.activities

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.NavArgs
import androidx.navigation.navArgs
import com.purabmodi.agrihelp.R
import com.purabmodi.agrihelp.databinding.ActivityAddUpdateItemBinding
import com.purabmodi.agrihelp.db.InventoryItem
import com.purabmodi.agrihelp.ui.viewModel.InventoryViewModel

class AddUpdateItem : AppCompatActivity() {
    private lateinit var binding : ActivityAddUpdateItemBinding
    private var date = 0L
    private val vm by viewModels<InventoryViewModel>()
    private var mode = "add"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddUpdateItemBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        initUI()

    }

    private fun initUI() {
//        intent.extras
        if(intent.hasExtra("mode")){
            mode = intent.getStringExtra("mode").toString()
            if(mode == "add"){
                binding.submitBtn.text = "Add Item"
            }else{
                binding.submitBtn.text = "Update Item"
                date = intent.getLongExtra("date",0L)
            }
        }
        binding.apply{
            submitBtn.setOnClickListener {
                if (itemName.text.toString().isNotEmpty() && itemBio.text.toString().isNotEmpty() && itemQuantity.text.toString().isNotEmpty() && date != 0L){
                    if(mode == "add"){
                        vm.insertItem(InventoryItem(0,itemName.text.toString(),itemBio.text.toString(),itemQuantity.text.toString().toFloat(),date))
                    }else{
                        vm.updateItem(InventoryItem(0,itemName.text.toString(),itemBio.text.toString(),itemQuantity.text.toString().toFloat(),date))
                    }
                }
            }
        }
    }
//    create a function for datepicker
    private fun showDatePicker(){
        val datePicker = DatePickerDialog(this)
        datePicker.setOnDateSetListener { view, year, month, dayOfMonth ->
            date = (year*10000 + month*100 + dayOfMonth).toLong()
        }
        datePicker.show()
    }

//    private fun datePicker() {
//        val datepickerdialog = DatePickerDialog(
//            requireActivity(),
//            { _, year, monthOfYear, dayOfMonth ->
//                // Display Selected date in textbox
//                cal.set(year, monthOfYear, dayOfMonth)
//                date.value = cal.timeInMillis
//                day = d
//                week = cal.get(Calendar.WEEK_OF_YEAR) - 1
//                month = cal.get(Calendar.MONTH) + 1
//                this.year = cal.get(Calendar.YEAR)
//            },
//            y,
//            m,
//            d
//        )
//        datepickerdialog.show()
//    }
}