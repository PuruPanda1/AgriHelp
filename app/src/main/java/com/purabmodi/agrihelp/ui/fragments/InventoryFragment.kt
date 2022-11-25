package com.purabmodi.agrihelp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import com.purabmodi.agrihelp.R
import com.purabmodi.agrihelp.databinding.FragmentInventoryBinding
import com.purabmodi.agrihelp.db.InventoryItem
import com.purabmodi.agrihelp.ui.adapter.InventoryAdapter
import com.purabmodi.agrihelp.ui.viewModel.InventoryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InventoryFragment : Fragment() {
    private var _binding : FragmentInventoryBinding? = null
    private val binding get() = _binding!!
    private val vm by viewModels<InventoryViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInventoryBinding.inflate(layoutInflater,container,false)

        initUI()

        return binding.root
    }

    private fun initUI() {
        val adapter = InventoryAdapter(
            updateItem = {item->
                updateItem(item)
            },
            deleteItem = {item ->  
                deleteItem(item)
            }
        )
        binding.inventoryRc.layoutManager = LinearLayoutManager(requireContext())
        binding.inventoryRc.adapter = adapter

        vm.items.observe(viewLifecycleOwner){
            adapter.submitList(it)
        }

    }

    private fun deleteItem(item: InventoryItem) {
        vm.deleteItem(item)
    }

    private fun updateItem(item: InventoryItem) {
        vm.insertItem(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}