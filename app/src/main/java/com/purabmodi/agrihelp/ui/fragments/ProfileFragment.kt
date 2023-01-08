package com.purabmodi.agrihelp.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat.recreate
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.purabmodi.agrihelp.R
import com.purabmodi.agrihelp.databinding.FragmentProfileBinding
import com.purabmodi.agrihelp.ui.activities.IntroductionActivity
import com.purabmodi.agrihelp.utility.SharedPref
import java.util.*

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val databaseReference = FirebaseDatabase.getInstance().reference.child("Users")
    private lateinit var loadingDialogFragment: LoadingDialogFragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        initUI()

        return binding.root
    }

    private fun initUI() {
        loadingDialogFragment = LoadingDialogFragment()
        loadingDialogFragment.show(childFragmentManager, "Loading Dialog")
        loadData()
        binding.profilelogout.setOnClickListener {
            signOut()
        }
        binding.editButton.setOnClickListener {
            changeLanguage()
        }
    }

    private fun signOut() {
        SharedPref(requireContext()).setLoggedIn(false)
        FirebaseAuth.getInstance().signOut()
        startActivity(Intent(requireActivity(),IntroductionActivity::class.java))
        requireActivity().finish()
    }

    private fun loadData() {
        val currentUser = FirebaseAuth.getInstance().currentUser!!.uid
        databaseReference.child(currentUser).get().addOnSuccessListener {
            binding.profileUserName.text = it.child("username").value.toString()
            binding.profileEmailId.text = it.child("email").value.toString()
            loadingDialogFragment.dismiss()
        }
    }
    private fun changeLanguage() {
        val language = listOf("English","Gujarati","Hindi")

        val alterDialogBuilder = MaterialAlertDialogBuilder(requireContext())
        alterDialogBuilder.setTitle("Choose Language")
        alterDialogBuilder.setSingleChoiceItems(language.toTypedArray(), -1) { dialog, which ->
            when(which) {
                0 -> {
                    SharedPref(requireContext()).setLanguage("en")
                    setLocale("en")
                    dialog.dismiss()
                }
                1 -> {
                    SharedPref(requireContext()).setLanguage("gu")
                    setLocale("gu")
                    dialog.dismiss()
                }
                2 -> {
                    SharedPref(requireContext()).setLanguage("hi")
                    setLocale("hi")
                    dialog.dismiss()
                }
            }
        }
        alterDialogBuilder.create()
        alterDialogBuilder.show()
    }

    private fun setLocale(lang:String) {
        val sharedPref = SharedPref(requireContext())
        sharedPref.setLanguage(lang)
        val locale = Locale(lang)
        Locale.setDefault(locale)

        val config = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
        recreate(requireActivity())
    }

}