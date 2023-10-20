package com.mohamedmabrouk.letsplant.ui.reminders

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.mohamedmabrouk.letsplant.R
import com.mohamedmabrouk.letsplant.data.Reminder
import com.mohamedmabrouk.letsplant.data.source.local.ReminderStatus
import com.mohamedmabrouk.letsplant.data.source.local.ReminderType
import com.mohamedmabrouk.letsplant.util.DateTimeUtils
import com.mohamedmabrouk.letsplant.util.LocaleHelper
import java.util.*


class RemindersListAdapter(
    private val context: Context,
    private val remindersList: MutableList<Reminder>,
    private val reminderClickListener: ReminderClickListener?,
    private val reminderStatus: ReminderStatus
) : RecyclerView.Adapter<RemindersListAdapter.ViewHolder>() {
    //todo: enhance recycler against repeated items

    private val localeHelper = LocaleHelper(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_plant_reminder, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val reminder: Reminder = remindersList[position]

        when (reminderStatus) {
            ReminderStatus.TODAY -> {
                holder.markDoneButton.visibility = View.VISIBLE
                holder.doneImageView.visibility = View.INVISIBLE
                holder.dateTextView.visibility = View.GONE
            }
            else -> {
                holder.markDoneButton.visibility = View.GONE
                holder.doneImageView.visibility = View.INVISIBLE
                holder.dateTextView.visibility = View.VISIBLE
            }

        }

        holder.plantNameTextView.text = when (reminder.reminderType) {
            ReminderType.FERTILIZE -> context.getString(
                R.string.fertilize_plant_,
                reminder.plantName
            )
            else -> context.getString(R.string.water_plant_, reminder.plantName)
        }
        holder.dateTextView.text = DateTimeUtils.getDateString(
            reminder.date,
            DateTimeUtils.REMINDER_DATE_FORMAT,
            Locale(localeHelper.getSelectedLanguageName())
        )

        if (reminder.reminderType == ReminderType.FERTILIZE)
            holder.reminderTypeImageView.setImageResource(R.drawable.fertilize)
        else
            holder.reminderTypeImageView.setImageResource(R.drawable.watering)

        if (reminder.status == ReminderStatus.DONE) {
            holder.markDoneButton.visibility = View.GONE
            holder.doneImageView.visibility = View.VISIBLE
        }

        holder.markDoneButton.setOnClickListener {
            remindersList[position].status = ReminderStatus.DONE
            holder.markDoneButton.visibility = View.GONE
            holder.doneImageView.visibility = View.VISIBLE
            reminderClickListener?.onMarkDoneItemClick(reminder, it)
        }
    }

    override fun getItemCount(): Int {
        return remindersList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val cardView: CardView = itemView.findViewById(R.id.cv_container)
        val reminderTypeImageView: ImageView = itemView.findViewById(R.id.iv_plant)
        val doneImageView: ImageView = itemView.findViewById(R.id.iv_done)
        val plantNameTextView: TextView = itemView.findViewById(R.id.tv_title)
        val dateTextView: TextView = itemView.findViewById(R.id.tv_date)
        val markDoneButton: Button = itemView.findViewById(R.id.btn_mark_done)
    }

    interface ReminderClickListener {
        fun onMarkDoneItemClick(reminder: Reminder, view: View)
    }
}