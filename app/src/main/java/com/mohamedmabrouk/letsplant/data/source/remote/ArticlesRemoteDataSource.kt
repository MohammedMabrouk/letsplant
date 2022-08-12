package com.mohamedmabrouk.letsplant.data.source.remote

import android.content.Context
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.mohamedmabrouk.letsplant.data.Article
import com.mohamedmabrouk.letsplant.data.source.ArticlesDataSource
import com.mohamedmabrouk.letsplant.firebase.ErrorCodeMapper
import com.mohamedmabrouk.letsplant.firebase.RealtimeDbManager
import com.mohamedmabrouk.letsplant.util.ConnectivityUtils
import com.mohamedmabrouk.letsplant.util.LocaleHelper
import com.mohamedmabrouk.letsplant.util.ResourceManager
import dagger.Module
import javax.inject.Inject

@Module
class ArticlesRemoteDataSource @Inject constructor(val context: Context) : ArticlesDataSource {
    private val TAG = "ArticlesRemoteDS"

    var realtimeDbManager: RealtimeDbManager = RealtimeDbManager(context)
    var errorCodeMapper: ErrorCodeMapper =
        ErrorCodeMapper(ResourceManager(context, LocaleHelper(context)))

    override fun getArticles(
        callback: ArticlesDataSource.LoadArticlesCallback,
        articlesType: Int,
        languageCode: String
    ) {
        val articlesTypeString = when (articlesType) {
            0 -> "PlantsCare"
            else -> "PlantsCare"
        }
        val DB_PATH = "Articles/$articlesTypeString/$languageCode"
        Log.v(TAG, "requesting: $DB_PATH")
        if (ConnectivityUtils.isNetworkAvailable(context)) {
            realtimeDbManager.getDbReference(DB_PATH)
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        val articlesList = ArrayList<Article>()
                        var article: Article?
                        for (articleSnapshot: DataSnapshot in dataSnapshot.children) {
                            article = articleSnapshot.getValue(Article::class.java)
                                .apply { this?.id = articleSnapshot.key?.toLong() }

                            if (article != null)
                                articlesList.add(article)
                        }

                        if (articlesList.isEmpty())
                            callback.onDataNotAvailable()
                        else
                            callback.onArticlesLoaded(articlesList)
                    }

                    override fun onCancelled(error: DatabaseError) {
                        callback.onError(errorCodeMapper.getFirebaseDataBaseError(error.code))
                    }
                })
        } else {
            callback.onError(errorCodeMapper.getFirebaseDataBaseError(DatabaseError.DISCONNECTED))
        }

    }
}