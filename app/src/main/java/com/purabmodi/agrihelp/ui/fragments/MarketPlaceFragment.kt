package com.purabmodi.agrihelp.ui.fragments

import android.app.ProgressDialog
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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.purabmodi.agrihelp.R
import com.purabmodi.agrihelp.data.models.Posts
import com.purabmodi.agrihelp.data.models.Record
import com.purabmodi.agrihelp.databinding.FragmentMarketPlaceBinding
import com.purabmodi.agrihelp.ui.adapter.ForumAdapter
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMarketPlaceBinding.inflate(layoutInflater, container, false)

        initUI()

        return binding.root
    }

    private fun initUI() {
        val adapter = ForumAdapter()
        binding.forumRC.adapter = adapter
        binding.forumRC.layoutManager = LinearLayoutManager(requireContext())
        val arrayList = ArrayList<Posts>()
        val db = Firebase.firestore

        binding.forumRefresh.setOnRefreshListener {
            Toast.makeText(requireContext(), "Refresh", Toast.LENGTH_SHORT).show()
        }

        binding.forumRefresh.isRefreshing=false

        db.collection("Posts")
            .get()
            .addOnSuccessListener {
                for (document in it) {
                    val record = Posts(
                        document.data["comments"].toString().toLong(),
                        document.data["date"].toString(),
                        document.data["description"].toString(),
                        document.data["hashtags"].toString(),
                        document.data["id"].toString(),
                        document.data["likes"].toString().toLong(),
                        document.data["title"].toString(),
                        document.data["username"].toString(),
                        document.data["userId"].toString()
                    )
                    arrayList.add(record)
                }
                adapter.submitList(arrayList)
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}