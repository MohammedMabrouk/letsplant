package com.mohamedmabrouk.letsplant.ui.discover

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.mohamedmabrouk.letsplant.R
import com.mohamedmabrouk.letsplant.data.Discover
import com.mohamedmabrouk.letsplant.util.Constants

class DiscoverItemsAdapter(
    private val discoverItemsList: List<Discover>,
    private val discoverItemsClickListener: DiscoverItemClickListener
) : RecyclerView.Adapter<DiscoverItemsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_discover_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val discoverItem = discoverItemsList[position]
        holder.imageView.setImageResource(discoverItem.img)
        holder.textView.text = discoverItem.text
        holder.cardView.setOnClickListener {
            discoverItemsClickListener.onItemClick(discoverItem.itemType, it)
        }
    }

    override fun getItemCount(): Int {
        return discoverItemsList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val cardView: CardView = itemView.findViewById(R.id.cv_container)
        val imageView: ImageView = itemView.findViewById(R.id.item_iv)
        val textView: TextView = itemView.findViewById(R.id.item_tv)
    }

    interface DiscoverItemClickListener {
        fun onItemClick(itemType: Constants.DiscoverItem, view: View)
    }
}