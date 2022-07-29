package com.mohamedmabrouk.letsplant.ui.discover.plants

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.mohamedmabrouk.letsplant.R


class PlantItemsAdapter(
    private val plantItemsList: List<PlantItemModel>,
    private val plantItemsClickListener: PlantItemsClickListener
) : RecyclerView.Adapter<PlantItemsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_plant_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val plantItem = plantItemsList[position]
        holder.imageView.setImageResource(plantItem.img)
        holder.textView.text = plantItem.title
        holder.cardView.setOnClickListener {
            plantItemsClickListener.onItemClick(position, it)
        }
    }

    override fun getItemCount(): Int {
        return plantItemsList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val cardView: CardView = itemView.findViewById(R.id.cv_container)
        val imageView: ImageView = itemView.findViewById(R.id.item_iv)
        val textView: TextView = itemView.findViewById(R.id.item_tv)
    }

    interface PlantItemsClickListener {
        fun onItemClick(position: Int, view: View)
    }
}