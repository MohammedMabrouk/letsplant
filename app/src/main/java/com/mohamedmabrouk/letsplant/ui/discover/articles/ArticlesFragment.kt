package com.mohamedmabrouk.letsplant.ui.discover.articles

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.mohamedmabrouk.letsplant.R
import com.mohamedmabrouk.letsplant.data.Article
import com.mohamedmabrouk.letsplant.databinding.FragmentArticlesBinding
import com.mohamedmabrouk.letsplant.util.Constants
import com.mohamedmabrouk.letsplant.util.LocaleHelper
import com.mohamedmabrouk.letsplant.util.hideLoadingIndicator
import com.mohamedmabrouk.letsplant.util.showLoadingIndicator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArticlesFragment : Fragment(), ArticleItemAdapter.ArticleItemsClickListener {

    companion object {
        fun newInstance() = ArticlesFragment()
    }

    private var _binding: FragmentArticlesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: ArticlesViewModel by activityViewModels()
    private var ARTICLES_TYPE: Int = 0


    private lateinit var SELECTED_LANGUAGE_CODE : String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentArticlesBinding.inflate(inflater, container, false)
        ARTICLES_TYPE = arguments?.getInt(Constants.ARTICLES_TYPE) ?: 0
        SELECTED_LANGUAGE_CODE = LocaleHelper(activity as Context).getSelectedLanguageName()
        return binding.root
    }

//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProvider(this).get(ArticlesViewModel::class.java)
//        // TODO: Use the ViewModel
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Modify Header Title
        _binding?.pageTitle?.tvTitle!!.text = when(ARTICLES_TYPE){
            0 -> getString(R.string.plants_care)
            else -> getString(R.string.plants_care)
        }


        viewModel.dataLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading)
                showLoadingIndicator(this)
            else
                hideLoadingIndicator(this)
        }

        viewModel.items.observe(viewLifecycleOwner) {
            if (it == null)
                _binding?.rvArticles!!.visibility = View.INVISIBLE
            else {
                _binding?.rvArticles!!.layoutManager = GridLayoutManager(activity, 2)
                val adapter = ArticleItemAdapter(it, this)
                _binding?.rvArticles!!.adapter = adapter
                _binding?.rvArticles!!.visibility = View.VISIBLE
            }
        }

        viewModel.empty.observe(viewLifecycleOwner) { isEmpty ->
            if (!isEmpty)
                _binding?.errorContainer!!.root.visibility = View.GONE
            else {
                _binding?.errorContainer!!.tvTitle.text = getString(R.string.no_items)
                _binding?.errorContainer!!.btnRetry.setOnClickListener {
                    getArticleList()
                }
                _binding?.errorContainer!!.root.visibility = View.VISIBLE
            }
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            if (error == null)
                _binding?.errorContainer!!.root.visibility = View.GONE
            else {
                _binding?.errorContainer!!.tvTitle.text = error
                _binding?.errorContainer!!.btnRetry.setOnClickListener {
                    getArticleList()
                }
                _binding?.errorContainer!!.root.visibility = View.VISIBLE
            }
        }

    }

    override fun onResume() {
        super.onResume()
        getArticleList()
    }

    private fun getArticleList(){
        viewModel.getArticles(ARTICLES_TYPE, SELECTED_LANGUAGE_CODE)
    }

    private fun openArticleDetails(article: Article, view: View) {
        //todo: complete
        val bundle = bundleOf("article" to article)
        Navigation.findNavController(view)
            .navigate(R.id.action_articles_dest_to_articleDetailsFragment, bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(article: Article, view: View) {
        openArticleDetails(article, view)
    }

}