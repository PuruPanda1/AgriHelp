package com.purabmodi.agrihelp.ui.fragments

import android.graphics.Bitmap
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.purabmodi.agrihelp.R
import com.purabmodi.agrihelp.databinding.FragmentWebViewBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class WebViewFragment : Fragment() {
    private var _binding: FragmentWebViewBinding? = null
    private val binding get() = _binding!!
    private val args: WebViewFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWebViewBinding.inflate(layoutInflater, container, false)
        binding.webView.loadUrl(args.link)
        binding.heading.text = args.source
        binding.webView.webViewClient = WebViewClient()

        var x1 = 0f
        var x2 = 0f

        binding.webView.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> x1 = event.x
                MotionEvent.ACTION_UP -> {
                    x2 = event.x
                    val distance = Math.abs(x2 - x1)
                    if (distance >= 150 && x2 > x1) {
                        findNavController().navigateUp()
                        true
                    }
                }
            }
            false
        }

        binding.closeBtn.setOnClickListener {
            findNavController().navigateUp()
        }
        return binding.root
    }
    inner class WebViewClient : android.webkit.WebViewClient() {

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            binding.progressBar.isVisible = true
        }

        //         ProgressBar will disappear once page is loaded
        override fun onPageFinished(view: WebView, url: String) {
            super.onPageFinished(view, url)
            binding.progressBar.progress = 80
            lifecycleScope.launch {
                delay(200)
                binding.progressBar.visibility = View.GONE
            }
        }
    }
}