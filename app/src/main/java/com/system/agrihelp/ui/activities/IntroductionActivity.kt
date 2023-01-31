package com.system.agrihelp.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.system.agrihelp.MainActivity
import com.system.agrihelp.R
import com.system.agrihelp.databinding.ActivityIntroductionBinding
import com.system.agrihelp.ui.adminstration.LoginActivity
import com.system.agrihelp.ui.adminstration.SignUpActivity
import com.system.agrihelp.utility.SharedPref
import java.util.*

class IntroductionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityIntroductionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntroductionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = ContextCompat.getColor(this, R.color.mainScreenBlue)

        binding.changeLanguage.setOnClickListener {
            changeLanguage()
        }


        binding.register.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        binding.signIn.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

    }

    private fun changeLanguage() {
        val language = listOf("English","Gujarati","Hindi")

        val alterDialogBuilder = MaterialAlertDialogBuilder(this)
        alterDialogBuilder.setTitle("Choose Language")
        alterDialogBuilder.setSingleChoiceItems(language.toTypedArray(), -1) { dialog, which ->
            when(which) {
                0 -> {
                    SharedPref(this).setLanguage("en")
                    setLocale("en")
                    dialog.dismiss()
                }
                1 -> {
                    SharedPref(this).setLanguage("gu")
                    setLocale("gu")
                    dialog.dismiss()
                }
                2 -> {
                    SharedPref(this).setLanguage("hi")
                    setLocale("hi")
                    dialog.dismiss()
                }
            }
        }
        alterDialogBuilder.create()
        alterDialogBuilder.show()
    }

    fun setLocale(lang:String) {
        val sharedPref = SharedPref(this)
        sharedPref.setLanguage(lang)
        val locale = Locale(lang)
        Locale.setDefault(locale)

        val config = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
        recreate()
    }

    override fun onStart() {
        super.onStart()
        Log.d("LOGGEDiN", "onStart: ${SharedPref(this).isLoggedIn()}")
        if (SharedPref(this).isLoggedIn()) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}