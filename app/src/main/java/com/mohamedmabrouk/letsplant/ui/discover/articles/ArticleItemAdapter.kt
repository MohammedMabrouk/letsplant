package com.mohamedmabrouk.letsplant.ui.discover.articles

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.mohamedmabrouk.letsplant.R


class ArticleItemAdapter(
    private val articleItemsList: List<ArticleItemModel>,
    private val articleItemsClickListener: ArticleItemsClickListener
) : RecyclerView.Adapter<ArticleItemAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_plant_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val articleItem = articleItemsList[position]
        holder.imageView.setImageResource(articleItem.img)
        holder.textView.text = articleItem.title
        holder.cardView.setOnClickListener {
            articleItemsClickListener.onItemClick(position, it)
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
        fun onItemClick(position: Int, view: View)
    }
}