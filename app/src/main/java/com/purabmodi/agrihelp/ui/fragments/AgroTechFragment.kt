package com.purabmodi.agrihelp.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.purabmodi.agrihelp.R
import com.purabmodi.agrihelp.databinding.FragmentAgroTechBinding
import com.purabmodi.agrihelp.ui.viewModel.NewsViewModel
import com.purabmodi.agrihelp.utility.Resource
import com.purabmodi.agrihelp.utility.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AgroTechFragment : Fragment() {
    private var _binding: FragmentAgroTechBinding? = null
    private val binding get() = _binding!!
    private val vm by viewModels<NewsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAgroTechBinding.inflate(inflater, container, false)

        initUI()

        subscribeToObservers()

        return binding.root
    }

    private fun subscribeToObservers() {
        vm.news.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let { newsResponse ->
                        Log.d(
                            "ResponseTechNews", "subscribeToObservers: ${
                                newsResponse.articles.map { article ->
                                    article.title
                                }
                            }"
                        )
                    }
                }
                Status.ERROR -> {
                    Toast.makeText(requireContext(), "Error ${it.message}", Toast.LENGTH_SHORT)
                        .show()
                }
                Status.LOADING -> {
                }
            }
        }
    }

    private fun initUI() {
        vm.getNews(1)
    }
}