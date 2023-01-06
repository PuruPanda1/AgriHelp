package com.purabmodi.agrihelp.ui.adminstration

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.purabmodi.agrihelp.MainActivity
import com.purabmodi.agrihelp.databinding.ActivityLoginBinding
import com.purabmodi.agrihelp.ui.fragments.LoadingDialogFragment
import com.purabmodi.agrihelp.utility.SharedPref
import kotlin.Boolean
import kotlin.toString


class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var progressDialog: LoadingDialogFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()

    }

    private fun initUI() {
        actionBar?.hide()
        progressDialog = LoadingDialogFragment()
        binding.login.setOnClickListener {
            login()
        }
    }

    private fun login() {
        if (check()) {
            progressDialog.show(supportFragmentManager, progressDialog.tag)

            val mAuth = FirebaseAuth.getInstance()

            mAuth.signInWithEmailAndPassword(
                binding.email.text.toString(),
                binding.password.text.toString()
            )
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(applicationContext, "Login Successful", Toast.LENGTH_SHORT)
                            .show()
                        progressDialog.dismiss()
                        val sharedPref = SharedPref(this)
                        sharedPref.setLoggedIn(true)
                        getUserName(mAuth.currentUser?.uid.toString())
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    } else {
                        val message = task.exception.toString()
                        Toast.makeText(applicationContext, "Error: $message", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
        } else {
            Toast.makeText(applicationContext, "Fields can not be empty", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getUserName(userId: String) {
        val mDatabase = FirebaseDatabase.getInstance().reference
        mDatabase.child("users").child(userId).get().addOnSuccessListener {
            Log.i("firebase", "Got value ${it.value}")
        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }
    }

    private fun check(): Boolean {
        if (binding.email.text.toString().isNotBlank() && binding.password.text.toString()
                .isNotBlank()
        ) {
            return true
        }
        return false
    }
}