package com.system.agrihelp.ui.activities

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.system.agrihelp.R
import com.system.agrihelp.databinding.ActivityAddUpdateItemBinding
import com.system.agrihelp.db.InventoryItem
import com.system.agrihelp.ui.viewModel.InventoryViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class AddUpdateItem : AppCompatActivity() {
    private lateinit var binding: ActivityAddUpdateItemBinding
    private var date = 0L
    private var formatedDate = ""
    val sdf = SimpleDateFormat("dd/MM/yyyy")
    private val vm by viewModels<InventoryViewModel>()
    private var mode = "add"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddUpdateItemBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

//        code to convert date to string
        date = System.currentTimeMillis()
        val netDate = Date(date)
        formatedDate = sdf.format(netDate)

        initUI()

    }

    private fun initUI() {
//        intent.extras
        if (intent.hasExtra("mode")) {
            mode = intent.getStringExtra("mode").toString()
            if (mode == "add") {
                binding.submitBtn.text = "Add Item"
            } else {
                binding.submitBtn.text = "Update Item"
                date = intent.getLongExtra("date", 0L)
            }
        }
        binding.apply {
            dateTv.text = formatedDate
            calenderPicker.setOnClickListener {
                showDatePicker()
            }
            submitBtn.setOnClickListener {
                if (itemName.text.toString().isNotEmpty() && itemBio.text.toString()
                        .isNotEmpty() && itemQuantity.text.toString().isNotEmpty() && date != 0L
                ) {
                    if (mode == "add") {
                        vm.insertItem(
                            InventoryItem(
                                0,
                                itemName.text.toString(),
                                itemBio.text.toString(),
                                false,
                                itemQuantity.text.toString().toFloat(),
                                date
                            )
                        )
                    } else {
                        vm.updateItem(
                            InventoryItem(
                                0,
                                itemName.text.toString(),
                                itemBio.text.toString(),
                                true,
                                itemQuantity.text.toString().toFloat(),
                                date
                            )
                        )
                    }
                    if(mode=="add"){
                        Toast.makeText(applicationContext, "Added", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(applicationContext, "Edited" +
                                "", Toast.LENGTH_SHORT).show()
                    }
                    finish()
                } else {
                    Toast.makeText(applicationContext, "SOMETHING WENT WRONG", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    //    create a function for datepicker
    private fun showDatePicker() {
        val datePicker = DatePickerDialog(this)
        datePicker.setOnDateSetListener { view, year, month, dayOfMonth ->
            date = (year * 10000 + month * 100 + dayOfMonth).toLong()
            val netDate = Date(date)
            formatedDate = sdf.format(netDate)
            binding.dateTv.text = getString(R.string.date,formatedDate)
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