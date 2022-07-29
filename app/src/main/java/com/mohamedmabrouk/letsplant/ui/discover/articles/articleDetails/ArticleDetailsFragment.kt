package com.mohamedmabrouk.letsplant.ui.discover.articles.articleDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.mohamedmabrouk.letsplant.R

class ArticleDetailsFragment : Fragment() {

    companion object {
        fun newInstance() = ArticleDetailsFragment()
    }

    private lateinit var viewModel: ArticleDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_article_details, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ArticleDetailsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}