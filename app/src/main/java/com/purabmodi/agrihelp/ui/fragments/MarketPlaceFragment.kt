package com.purabmodi.agrihelp.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.purabmodi.agrihelp.data.models.Posts
import com.purabmodi.agrihelp.databinding.FragmentMarketPlaceBinding
import com.purabmodi.agrihelp.ui.adapter.ForumAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MarketPlaceFragment : Fragment() {
    private var _binding: FragmentMarketPlaceBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: ForumAdapter
    private lateinit var db: FirebaseFirestore
    private lateinit var posts: ArrayList<Posts>
    private lateinit var loadingDialogFragment: LoadingDialogFragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMarketPlaceBinding.inflate(layoutInflater, container, false)

        initUI()

        return binding.root
    }

    private fun initUI() {
        loadingDialogFragment = LoadingDialogFragment()
        loadingDialogFragment.show(childFragmentManager, "Loading Dialog")
        adapter = ForumAdapter()
        binding.forumRC.adapter = adapter
        binding.forumRC.layoutManager = LinearLayoutManager(requireContext())
        posts = ArrayList()
        db = Firebase.firestore

        getPosts()

        binding.forumRefresh.setOnRefreshListener {
            loadingDialogFragment.show(childFragmentManager, "Loading Dialog")
            getPosts()
        }

    }

    private fun getPosts() {
        db.collection("Posts")
            .orderBy("date")
            .get()
            .addOnSuccessListener {
                for (document in it) {
                    val post = document.toObject(Posts::class.java)
                    posts.add(post)
                }
                adapter.submitList(posts)
                loadingDialogFragment.dismiss()
                binding.forumRefresh.isRefreshing = false
            }
            .addOnFailureListener {
                binding.forumRefresh.isRefreshing = false
                Toast.makeText(requireContext(), "Error: ${it.message}", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}