package com.purabmodi.agrihelp.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.purabmodi.agrihelp.databinding.ActivityCreatePostBinding
import com.purabmodi.agrihelp.ui.fragments.LoadingDialogFragment
import com.purabmodi.agrihelp.utility.SharedPref
import java.util.*

class CreatePostActivity : AppCompatActivity() {
    private lateinit var loadingProgress: LoadingDialogFragment
    private lateinit var binding: ActivityCreatePostBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreatePostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()

    }

    private fun initUI() {
        loadingProgress = LoadingDialogFragment()
        binding.postButton.setOnClickListener {
            if (check()) {
                savePost()
            }
        }
    }

    private fun savePost() {

        var userName = SharedPref(this).getUserName()
        val db = Firebase.firestore
        val id: String = UUID.randomUUID().toString()
        val currentUserId = FirebaseAuth.getInstance().currentUser!!.uid
        loadingProgress.show(supportFragmentManager, loadingProgress.tag)
        db.collection("Posts").document(id).set(
            hashMapOf(
                "id" to id,
                "title" to binding.blogTitle.text.toString(),
                "description" to binding.blogDescription.text.toString(),
                "userId" to currentUserId,
                "hashtags" to binding.blogHashTags.text.toString(),
                "date" to Date(),
                "likes" to 0,
                "comments" to 0,
                "username" to userName
            )
        ).addOnSuccessListener {
            Toast.makeText(applicationContext, "Post Created Successfully", Toast.LENGTH_SHORT)
                .show()
            loadingProgress.dismiss()
            finish()
        }.addOnFailureListener {
            Toast.makeText(applicationContext, "SOMETHING WENT WRONG", Toast.LENGTH_SHORT).show()
            loadingProgress.dismiss()
        }


    }

    private fun check(): Boolean {
        if (binding.blogTitle.text.toString()
                .isNotBlank() && binding.blogDescription.text.toString()
                .isNotBlank()
        ) {
            return true
        }
        return false
    }
}