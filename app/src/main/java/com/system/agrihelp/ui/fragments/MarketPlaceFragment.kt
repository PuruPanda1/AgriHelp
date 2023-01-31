package com.system.agrihelp.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.system.agrihelp.data.models.Posts
import com.system.agrihelp.databinding.FragmentMarketPlaceBinding
import com.system.agrihelp.ui.adapter.ForumAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MarketPlaceFragment : Fragment() {
    private var _binding: FragmentMarketPlaceBinding? = null
    private val binding get() = _binding!!
    private lateinit var forumAdapter: ForumAdapter
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
        forumAdapter = ForumAdapter(
            onComment = {
                onCommentClick(it)
            }
        )
        binding.forumRC.adapter = forumAdapter
        binding.forumRC.layoutManager = LinearLayoutManager(requireContext())
        posts = ArrayList()
        db = Firebase.firestore

        getPosts()

        binding.forumRefresh.setOnRefreshListener {
            loadingDialogFragment.show(childFragmentManager, "Loading Dialog")
            getPosts()
        }

    }

    private fun onLikeClick(it: Posts) {
        db.collection("Posts").document(it.id!!).update("likes", it.likes?.plus(1))
            .addOnSuccessListener {
                Toast.makeText(requireContext(), "Liked", Toast.LENGTH_SHORT).show()
            }
    }

    private fun onCommentClick(it: Posts) {
        val action = MarketPlaceFragmentDirections.actionMarketPlaceFragmentToCommentFragment(
            it.username!!,
            it.id!!,
            it.userId!!
        )
        findNavController().navigate(action)
    }

    private fun getPosts() {
        posts.clear()
        db.collection("Posts")
            .orderBy("date", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener {
                for (document in it) {
                    val post = document.toObject(Posts::class.java)
                    posts.add(post)
                }
                forumAdapter.submitList(posts)
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