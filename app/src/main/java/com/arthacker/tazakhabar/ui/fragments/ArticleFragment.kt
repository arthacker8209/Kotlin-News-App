package com.arthacker.tazakhabar.ui.fragments

import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.androiddevs.mvvmnewsapp.R
import com.arthacker.tazakhabar.ui.NewsActivity
import com.arthacker.tazakhabar.ui.NewsViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_article.*

class ArticleFragment : Fragment(R.layout.fragment_article) {

    lateinit var viewModel: NewsViewModel
    //will take take the argument which was passed in article fragment
    val args: ArticleFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as NewsActivity).viewModel
        val article = args.article
        webView.apply {
            //make sure that the article is loaded with this webView or not loaded in standard one
            webViewClient= WebViewClient()
            loadUrl(article.url)
        }
        fab.setOnClickListener{
            viewModel.saveNewsLocally(article)
            Snackbar.make(view,"Article Saved Successfully",Snackbar.LENGTH_SHORT).show()
        }
    }
}