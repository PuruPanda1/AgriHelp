package com.purabmodi.agrihelp.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.purabmodi.agrihelp.MainActivity
import com.purabmodi.agrihelp.R
import com.purabmodi.agrihelp.databinding.ActivityIntroductionBinding
import com.purabmodi.agrihelp.ui.adminstration.LoginActivity
import com.purabmodi.agrihelp.ui.adminstration.SignUpActivity
import com.purabmodi.agrihelp.utility.SharedPref

class IntroductionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityIntroductionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntroductionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        actionBar?.hide()

        binding.register.setOnClickListener {
            startActivity(Intent(this,SignUpActivity::class.java))
        }

        binding.signIn.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
        }

    }

    override fun onStart() {
        super.onStart()
        if (SharedPref(this).isLoggedIn()) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}