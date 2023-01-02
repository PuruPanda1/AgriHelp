package com.purabmodi.agrihelp.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.purabmodi.agrihelp.R
import com.purabmodi.agrihelp.data.models.Record
import com.purabmodi.agrihelp.databinding.FragmentMarketPlaceBinding
import com.purabmodi.agrihelp.ui.adapter.LatestMarketDataPagingAdapter
import com.purabmodi.agrihelp.ui.viewModel.MarketPlaceViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MarketPlaceFragment : Fragment() {
    private var _binding: FragmentMarketPlaceBinding? = null
    private val binding get() = _binding!!
    private val vm by viewModels<MarketPlaceViewModel>()
    private val map = HashMap<String, String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMarketPlaceBinding.inflate(layoutInflater, container, false)

        initUI()

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpMenu()
    }

    private fun setUpMenu() {
        (requireActivity() as MenuHost).addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.toolbar_searchview_menu, menu)
                val search = menu.findItem(R.id.menu_search)
                val searchView = search.actionView as androidx.appcompat.widget.SearchView
                searchView.isSubmitButtonEnabled = true
                searchView.setOnQueryTextListener(object :
                    androidx.appcompat.widget.SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        map["filters[commodity]"] = query ?: ""
                        vm.getMarketPlaceData(map)
                        return true
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        return false
                    }

                })
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                TODO("Not yet implemented")
            }

        })
    }


    private fun initUI() {
        val adapter = LatestMarketDataPagingAdapter(onClick = { item ->
            onCLick(item)
        })
        binding.marketPlaceRC.layoutManager = LinearLayoutManager(requireContext())
        binding.marketPlaceRC.adapter = adapter
        map["filters[grade]"] = "FAQ"
        vm.getMarketPlaceData(map)
        vm.marketPlaceData.observe(viewLifecycleOwner) {
            lifecycleScope.launch {
                adapter.submitData(it)
            }
        }
        lifecycleScope.launch {
            adapter.loadStateFlow.collectLatest {
                binding.marketPlaceRC.isVisible = it.refresh !is LoadState.Loading
                if (it.source.refresh is LoadState.Error) {
                    Toast.makeText(requireContext(), "Something went wrong", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun onCLick(item: Record) {
        Toast.makeText(requireContext(), "${item.commodity}", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}