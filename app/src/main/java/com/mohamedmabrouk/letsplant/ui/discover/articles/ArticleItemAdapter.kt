package com.mohamedmabrouk.letsplant.ui.discover.articles

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.mohamedmabrouk.letsplant.R
import com.mohamedmabrouk.letsplant.data.Article
import com.squareup.picasso.Picasso


class ArticleItemAdapter(
    private val articleItemsList: List<Article>,
    private val articleItemsClickListener: ArticleItemsClickListener
) : RecyclerView.Adapter<ArticleItemAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_plant_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val articleItem = articleItemsList[position]

        Picasso.get()
            .load(articleItem.imgUrl)
            .placeholder(R.drawable.green_tea_placeholder)
            .into(holder.imageView)

        holder.textView.text = articleItem.title
        holder.cardView.setOnClickListener {
            articleItemsClickListener.onItemClick(articleItem, it)
        }
    }

    override fun getItemCount(): Int {
        return articleItemsList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val cardView: CardView = itemView.findViewById(R.id.cv_container)
        val imageView: ImageView = itemView.findViewById(R.id.item_iv)
        val textView: TextView = itemView.findViewById(R.id.item_tv)
    }

    interface ArticleItemsClickListener {
        fun onItemClick(article: Article, view: View)
    }
}