package com.mohamedmabrouk.letsplant.ui.discover.plants

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.mohamedmabrouk.letsplant.R
import com.mohamedmabrouk.letsplant.data.Plant
import com.squareup.picasso.Picasso


class PlantItemsAdapter(
    private val plantItemsList: List<Plant>,
    private val plantItemsClickListener: PlantItemsClickListener
) : RecyclerView.Adapter<PlantItemsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_plant_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val plantItem = plantItemsList[position]

        Picasso.get()
            .load(plantItem.imgUrl)
            .placeholder(R.drawable.green_tea_placeholder)
            .into(holder.imageView)

        holder.textView.text = plantItem.name
        holder.cardView.setOnClickListener {
            plantItemsClickListener.onItemClick(plantItem, it)
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
        fun onItemClick(plant: Plant, view: View)
    }
}