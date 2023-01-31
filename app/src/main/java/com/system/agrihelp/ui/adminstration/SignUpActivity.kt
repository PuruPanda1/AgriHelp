package com.system.agrihelp.ui.adminstration

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.system.agrihelp.MainActivity
import com.system.agrihelp.databinding.ActivitySignUpBinding
import com.system.agrihelp.ui.fragments.LoadingDialogFragment
import com.system.agrihelp.utility.SharedPref

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var progressDialog: LoadingDialogFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)

        initUI()

        setContentView(binding.root)
    }

    private fun initUI() {

        actionBar?.hide()

        progressDialog = LoadingDialogFragment()

        binding.signInButton.setOnClickListener {
            createAccount()
        }

    }

    private fun createAccount() {
        if (check()) {
            progressDialog.show(supportFragmentManager, progressDialog.tag)

            val mAuth = FirebaseAuth.getInstance()

            mAuth.createUserWithEmailAndPassword(
                binding.email.text.toString(),
                binding.password.text.toString()
            )
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        saveUserInfo()

                    } else {
                        val message = task.exception.toString()
                        Toast.makeText(applicationContext, "Error: $message", Toast.LENGTH_SHORT)
                            .show()
                        mAuth.signOut()
                        progressDialog.dismiss()
                    }
                }


        } else {
            Toast.makeText(applicationContext, "Fields can not be empty", Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveUserInfo() {
        val currentUserId = FirebaseAuth.getInstance().currentUser!!.uid
        val userReference = FirebaseDatabase.getInstance().reference.child("Users")

        val userMap = HashMap<String, Any>()
        userMap["uid"] = currentUserId
        userMap["username"] = binding.username.text.toString()
        userMap["email"] = binding.email.text.toString()
        userMap["password"] = binding.password.text.toString()

        userReference.child(currentUserId).setValue(userMap)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {

                    Toast.makeText(
                        applicationContext,
                        "Account has been created successfully",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    progressDialog.dismiss()
                    val sharedPref = SharedPref(this)
                    sharedPref.setLoggedIn(true)
                    sharedPref.setUserName(binding.username.text.toString())
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()

                } else {
                    Toast.makeText(applicationContext, "SOMETHING WENT WRONG!", Toast.LENGTH_SHORT)
                        .show()
                    progressDialog.dismiss()
                }
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