package com.mohamedmabrouk.letsplant.ui.discover.articles

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mohamedmabrouk.letsplant.data.Article
import com.mohamedmabrouk.letsplant.data.source.ArticlesDataSource
import com.mohamedmabrouk.letsplant.data.source.remote.ArticlesRemoteDataSource
import javax.inject.Inject


class ArticlesViewModel @Inject constructor(application : Application) : AndroidViewModel(application) {

    val repository: ArticlesRepository = ArticlesRepository(ArticlesRemoteDataSource(application))

    private val _items = MutableLiveData<List<Article>>().apply { value = emptyList() }
    val items: LiveData<List<Article>>
        get() = _items

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean>
        get() = _dataLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?>
        get() = _error

    private val _empty = MutableLiveData<Boolean>()
    val empty: LiveData<Boolean>
        get() = _empty


    fun getArticles(articlesType: Int, languageCode: String){
        _dataLoading.value = true
        _empty.value = false
        _error.value = null
        _items.value = null

        repository.getArticles(object: ArticlesDataSource.LoadArticlesCallback{
            override fun onArticlesLoaded(articles: List<Article>?) {
                _dataLoading.value = false
                _empty.value = false
                _error.value = null
                _items.value = articles
            }

            override fun onDataNotAvailable() {
                _dataLoading.value = false
                _error.value = null
                _empty.value = true
            }

            override fun onError(error: String) {
                _dataLoading.value = false
                _empty.value = false
                _error.value = error
            }
        }, articlesType, languageCode)
    }

}