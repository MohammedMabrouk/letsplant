package com.mohamedmabrouk.letsplant.ui.myPlants

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.mohamedmabrouk.letsplant.R
import com.mohamedmabrouk.letsplant.data.UserPlant
import com.mohamedmabrouk.letsplant.util.LocaleHelper
import com.mohamedmabrouk.letsplant.util.NumbersUtility
import java.util.*


class MyPlantsAdapter(
    private val context: Context,
    private val plantItemsList: MutableList<UserPlant>,
    private val userPlantClickListener: UserPlantClickListener
) : RecyclerView.Adapter<MyPlantsAdapter.ViewHolder>() {
    private val TAG = "MyPlantsAdapter"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.d(TAG, "onCreateViewHolder: ")
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_user_plant_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder: $position")
        val plantItem : UserPlant = plantItemsList[position]
        holder.titleTextView.text = plantItem.name
        holder.wateringTextView.text = context.getString(R.string.repeat_watering_title_) +
                NumbersUtility.getFormattedInteger(plantItem.wateringRepeatCount.toString(), Locale(LocaleHelper(context).getSelectedLanguageName())) +
                context.getString(R.string.days)
        holder.fertilizeTextView.text = context.getString(R.string.repeat_fertilize_title_) +
                NumbersUtility.getFormattedInteger(plantItem.fertilizeRepeatCount.toString(), Locale(LocaleHelper(context).getSelectedLanguageName())) +
                context.getString(R.string.days)
        holder.deleteImageView.setOnClickListener {
            userPlantClickListener.onDeleteItemClick(plantItem, it)
            removeAt(position)
        }
    }

    override fun getItemCount(): Int {
        Log.d(TAG, "getItemCount: ${plantItemsList.size}")
        return plantItemsList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val cardView: CardView = itemView.findViewById(R.id.cv_container)
        val deleteImageView: ImageView = itemView.findViewById(R.id.iv_delete)
        val titleTextView: TextView = itemView.findViewById(R.id.tv_title)
        val wateringTextView: TextView = itemView.findViewById(R.id.tv_watering_title)
        val fertilizeTextView: TextView = itemView.findViewById(R.id.tv_fertilize_title)
    }

    interface UserPlantClickListener {
        fun onDeleteItemClick(plant: UserPlant, view: View)
    }

    fun removeAt(position: Int) {
        plantItemsList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, plantItemsList.size)
    }
}