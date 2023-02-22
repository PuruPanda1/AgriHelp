package com.purabmodi.agrihelp.ui.fragments

import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.purabmodi.agrihelp.databinding.FragmentInventoryBinding
import com.purabmodi.agrihelp.ui.adapter.LatestMarketDataPagingAdapter
import com.purabmodi.agrihelp.ui.viewModel.MarketPlaceViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InventoryFragment : Fragment() {
    private var _binding: FragmentInventoryBinding? = null
    private val binding get() = _binding!!
    private val vm by viewModels<MarketPlaceViewModel>()
    val map = HashMap<String, String>()
    private lateinit var adapter: LatestMarketDataPagingAdapter
    private val states = listOf(
        "--select state--",
        "Andhra Pradesh",
        "Arunachal Pradesh",
        "Assam",
        "Bihar",
        "Chhatisgarh",
        "Goa",
        "Gujarat",
        "Haryana",
        "Himachal Pradesh",
        "Jharkhand",
        "Karnataka",
        "Kerala",
        "Madhya Pradesh",
        "Maharashtra",
        "Manipur",
        "Meghalaya",
        "Mizoram",
        "Odisha",
        "Punjab",
        "Rajasthan",
        "Sikkim",
        "Tamil Naidu",
        "Telangana",
        "Tripura",
        "Uttarakhand",
        "Uttar Pradesh",
        "West Bengal"
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInventoryBinding.inflate(layoutInflater, container, false)

        initUI()
        subscribeToObservers()

        return binding.root
    }

    private fun subscribeToObservers() {
        vm.marketPlaceData.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
    }

    private fun initUI() {
        adapter = LatestMarketDataPagingAdapter(
            onClick = {
                onClick()
            }
        )
        getData(map)

        val stateAdapter = ArrayAdapter(
            requireContext(),
            androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
            states
        )
        binding.searchText.setAdapter(stateAdapter)
        binding.searchText.inputType = InputType.TYPE_NULL

        binding.inventoryRc.layoutManager = LinearLayoutManager(requireContext())
        binding.inventoryRc.adapter = adapter

        binding.searchText.onItemClickListener =
            object : android.widget.AdapterView.OnItemClickListener {
                override fun onItemClick(
                    parent: android.widget.AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val item = parent?.getItemAtPosition(position).toString()
                    if (item != "--select state--") {
                        map["filters[state]"] = item
                    } else {
                        map.remove("filters[state]")
                    }
                    getData(map)
                }
            }

    }

    private fun getData(map: java.util.HashMap<String, String>) {
        vm.getMarketPlaceData(map)
    }

    private fun onClick() {
        Toast.makeText(requireContext(), "Hey! Clicked", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}