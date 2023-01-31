package com.system.agrihelp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.system.agrihelp.data.models.Article
import com.system.agrihelp.databinding.FragmentAgroTechBinding
import com.system.agrihelp.ui.adapter.NewsAdapter
import com.system.agrihelp.ui.viewModel.NewsViewModel
import com.system.agrihelp.utility.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AgroTechFragment : Fragment() {
    private var _binding: FragmentAgroTechBinding? = null
    private val binding get() = _binding!!
    private val vm by viewModels<NewsViewModel>()
    private var page = 1

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
        val newsAdapter = NewsAdapter(
            onClick = {article->
                openWebView(article)
            }
        )
        binding.newsRCV.adapter = newsAdapter
        binding.newsRCV.layoutManager = LinearLayoutManager(requireContext())
        vm.news.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    newsAdapter.submitList(it.data?.articles)
                    binding.swipeRefresh.isRefreshing = false
                }
                Status.ERROR -> {
                    Toast.makeText(requireContext(), "Error ${it.message}", Toast.LENGTH_SHORT)
                        .show()
                }
                Status.LOADING -> {
                    binding.swipeRefresh.isRefreshing = true
                }
            }
        }
    }

    private fun openWebView(article: Article) {
        val action = AgroTechFragmentDirections.actionAgroTechFragmentToWebViewFragment(article.url,article.source.name)
        binding.root.findNavController().navigate(action)
    }

    private fun initUI() {
        vm.getNews(page)
        binding.swipeRefresh.setOnRefreshListener {
            vm.getNews(++page)
        }

    }
}