package com.system.agrihelp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.system.agrihelp.data.models.Comment
import com.system.agrihelp.databinding.FragmentCommentBinding
import com.system.agrihelp.ui.adapter.CommentAdapter
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class CommentFragment : Fragment() {
    private var _binding: FragmentCommentBinding? = null
    private val binding get() = _binding!!
    private val args by lazy { CommentFragmentArgs.fromBundle(requireArguments()) }

    private var commentArrayList = ArrayList<Comment>()
    private lateinit var db: FirebaseFirestore
    private lateinit var loadingDialogFragment: LoadingDialogFragment
    private lateinit var commentAdapter: CommentAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCommentBinding.inflate(inflater, container, false)

        initUI()

        return binding.root
    }

    private fun initUI() {
        loadingDialogFragment = LoadingDialogFragment()
        loadingDialogFragment.show(childFragmentManager, "Loading Dialog")
        commentAdapter = CommentAdapter(
            onComment = {
                Toast.makeText(requireContext(), "Hey Clicked", Toast.LENGTH_SHORT).show()
            }
        )

        binding.commentsBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.sendComment.setOnClickListener {
            sendComment()
        }

        binding.refreshComments.setOnRefreshListener {
            getComments()
        }

        binding.commentRC.layoutManager = LinearLayoutManager(requireContext())
        binding.commentRC.adapter = commentAdapter
        db = Firebase.firestore
        getComments()
    }

    private fun sendComment() {
        binding.commentRC.isVisible = false
        loadingDialogFragment.show(childFragmentManager, "Loading Dialog")
        val comment = binding.commentText.text.toString()
        if (comment.isNotEmpty()) {
            val id = UUID.randomUUID().toString()
            val commentMap = HashMap<String, Any>()
            commentMap["comment"] = comment
            commentMap["date"] = Date()
            commentMap["username"] = args.username
            commentMap["userId"] = args.userId
            db.collection("Posts")
                .document(args.postId)
                .collection("Comments")
                .document(id)
                .set(commentMap)
                .addOnSuccessListener {
                    binding.commentText.setText("")
                    getComments()
                }
                .addOnFailureListener {
                    Toast.makeText(requireContext(), "Error: ${it.message}", Toast.LENGTH_SHORT)
                        .show()
                    loadingDialogFragment.dismiss()
                }
        } else {
            Toast.makeText(requireContext(), "Please enter a comment", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getComments() {
        commentArrayList.clear()
        db.collection("Posts")
            .document(args.postId)
            .collection("Comments")
            .orderBy("date", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener {
                for (document in it) {
                    val comment = document.toObject(Comment::class.java)
                    commentArrayList.add(comment)
                }
                commentAdapter.submitList(commentArrayList)
                binding.refreshComments.isRefreshing = false
                loadingDialogFragment.dismiss()
                binding.commentRC.isVisible = true
            }
    }

}