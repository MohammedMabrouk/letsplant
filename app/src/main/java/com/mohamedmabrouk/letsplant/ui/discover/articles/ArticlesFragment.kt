package com.mohamedmabrouk.letsplant.ui.discover.articles

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.mohamedmabrouk.letsplant.R
import com.mohamedmabrouk.letsplant.databinding.FragmentArticlesBinding
import com.mohamedmabrouk.letsplant.util.Constants

class ArticlesFragment : Fragment(), ArticleItemAdapter.ArticleItemsClickListener {

    companion object {
        fun newInstance() = ArticlesFragment()
    }

    private var _binding: FragmentArticlesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var viewModel: ArticlesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentArticlesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ArticlesViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Modify Header Title
        _binding?.pageTitle?.tvTitle!!.text = when(arguments?.getInt(Constants.ARTICLES_TYPE)){
            0 -> getString(R.string.plants_care)
            else -> getString(R.string.plants_care)
        }

        _binding?.rvArticles!!.layoutManager = GridLayoutManager(activity, 2)

        val data = ArrayList<ArticleItemModel>()
        for(i in 1..10){
            data.add(ArticleItemModel(R.drawable.care, "article $i", ""))
        }

        val adapter = ArticleItemAdapter(data, this)
        _binding?.rvArticles!!.adapter = adapter

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(position: Int, view: View) {
        TODO("Not yet implemented")
    }

}