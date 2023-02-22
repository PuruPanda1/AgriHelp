package com.purabmodi.agrihelp.ui.bottomSheet

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.purabmodi.agrihelp.databinding.CreateNewLayoutBinding
import com.purabmodi.agrihelp.ui.activities.AddUpdateItem
import com.purabmodi.agrihelp.ui.activities.CreatePostActivity

class CreateNewBottomSheet : BottomSheetDialogFragment() {
    private var _binding: CreateNewLayoutBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CreateNewLayoutBinding.inflate(layoutInflater, container, false)

//        binding.addItemLayout.setOnClickListener {
//            requireActivity().startActivity(Intent(requireActivity(), AddUpdateItem::class.java))
//            dismiss()
//        }

        binding.addPostLayout.setOnClickListener {
            requireActivity().startActivity(
                Intent(
                    requireActivity(),
                    CreatePostActivity::class.java
                )
            )
            dismiss()
        }

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}