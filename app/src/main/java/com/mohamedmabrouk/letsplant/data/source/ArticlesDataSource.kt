package com.mohamedmabrouk.letsplant.data.source

import com.mohamedmabrouk.letsplant.data.Article

interface ArticlesDataSource {

    interface LoadArticlesCallback {

        fun onArticlesLoaded(articles: List<Article>?)

        fun onDataNotAvailable()

        fun onError(error: String)
    }

    fun getArticles(callback: LoadArticlesCallback, articlesType: Int, languageCode: String)
}