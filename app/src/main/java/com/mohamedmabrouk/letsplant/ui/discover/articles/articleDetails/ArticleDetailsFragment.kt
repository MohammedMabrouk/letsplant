package com.mohamedmabrouk.letsplant.ui.discover.articles.articleDetails

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.mohamedmabrouk.letsplant.R
import com.mohamedmabrouk.letsplant.data.Article
import com.mohamedmabrouk.letsplant.databinding.FragmentArticleDetailsBinding
import com.mohamedmabrouk.letsplant.util.ShareUtils
import com.squareup.picasso.Picasso

class ArticleDetailsFragment : Fragment() {

    companion object {
        fun newInstance() = ArticleDetailsFragment()
    }

    private var _binding: FragmentArticleDetailsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var viewModel: ArticleDetailsViewModel

    private lateinit var article: Article

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentArticleDetailsBinding.inflate(inflater, container, false)
        article = arguments?.get("article") as Article
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ArticleDetailsViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding?.ivBack!!.setOnClickListener { activity?.onBackPressed() }
        _binding?.ivShare!!.setOnClickListener {
            //todo: add content to store and plant detials
            ShareUtils.shareContent(activity as Context, "test content ")
        }

        _binding?.tvArticleTitle!!.text = article.title
        _binding?.tvDetails!!.text = article.details



        Picasso.get()
            .load(article.imgUrl)
            .placeholder(R.drawable.green_tea_placeholder)
            .into(_binding?.ivArticleImg)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}