package com.mohamedmabrouk.letsplant.ui.discover.articles

import com.mohamedmabrouk.letsplant.data.Article
import com.mohamedmabrouk.letsplant.data.source.ArticlesDataSource
import com.mohamedmabrouk.letsplant.data.source.remote.ArticlesRemoteDataSource
import javax.inject.Inject

class ArticlesRepository @Inject constructor(val articlesRemoteDataSource: ArticlesRemoteDataSource) :
    ArticlesDataSource {
    // todo: check to get data locally or from server
    override fun getArticles(
        callback: ArticlesDataSource.LoadArticlesCallback,
        articlesType: Int,
        languageCode: String
    ) {
        articlesRemoteDataSource.getArticles(object : ArticlesDataSource.LoadArticlesCallback {
            override fun onArticlesLoaded(articles: List<Article>?) {
                callback.onArticlesLoaded(articles)
            }

            override fun onDataNotAvailable() {
                callback.onDataNotAvailable()
            }

            override fun onError(error: String) {
                callback.onError(error)
            }
        }, articlesType, languageCode)
    }

}