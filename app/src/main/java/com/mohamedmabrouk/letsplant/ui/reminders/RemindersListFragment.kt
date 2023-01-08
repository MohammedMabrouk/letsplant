package com.mohamedmabrouk.letsplant.ui.reminders

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.mohamedmabrouk.letsplant.data.Reminder
import com.mohamedmabrouk.letsplant.data.source.local.ReminderStatus
import com.mohamedmabrouk.letsplant.databinding.FragmentRemindersListBinding
import com.mohamedmabrouk.letsplant.ui.myPlants.MyPlantsFragment
import com.mohamedmabrouk.letsplant.ui.reminders.RemindersFragment.Companion.ARG_OBJECT
import com.mohamedmabrouk.letsplant.util.DateTimeUtils
import com.mohamedmabrouk.letsplant.util.hideLoadingIndicator
import com.mohamedmabrouk.letsplant.util.showLoadingIndicator

class RemindersListFragment : Fragment(), RemindersListAdapter.ReminderClickListener {

    private val TAG = "RemindersListFragment"

    companion object {
        fun newInstance() = MyPlantsFragment()
    }

    private var _binding: FragmentRemindersListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: RemindersViewModel by activityViewModels()
    private lateinit var reminderStatus : ReminderStatus

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRemindersListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.takeIf { it.containsKey(ARG_OBJECT) }?.apply {
            reminderStatus = when(getInt(ARG_OBJECT)){
                0 -> ReminderStatus.TODAY
                else -> ReminderStatus.FUTURE
            }
        }


        viewModel.dataLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading)
                showLoadingIndicator(this)
            else
                hideLoadingIndicator(this)
        }

        viewModel.items.observe(viewLifecycleOwner) {
            if (it == null)
                _binding?.rvReminders!!.visibility = View.INVISIBLE
            else {
                _binding?.rvReminders!!.layoutManager = LinearLayoutManager(activity)
                val adapter = RemindersListAdapter(activity as Context, it, this, reminderStatus)
                _binding?.rvReminders!!.adapter = adapter
                _binding?.rvReminders!!.visibility = View.VISIBLE
            }

            Log.d(TAG, "onViewCreated: reminders list -> ${it.toString()}")
        }

        viewModel.empty.observe(viewLifecycleOwner) { isEmpty ->
            if (!isEmpty)
                _binding?.ivEmpty!!.visibility = View.GONE
            else
                _binding?.ivEmpty!!.visibility = View.VISIBLE
        }


//        viewModel.addNewReminder(Reminder(null, 0, "test22", DateTimeUtils.getCurrentDate(), 1, 1))
//        viewModel.addNewReminder(Reminder(null,0,"dasdasda2", DateTimeUtils.getDateWithAddedDays(2), 2, 1))
//        viewModel.addNewReminder(Reminder(null,0,"dasdasda3", DateTimeUtils.getDateWithAddedDays(3), 1, 1))
//        viewModel.addNewReminder(Reminder(null,0,"dasdasda4", DateTimeUtils.getCurrentDate(), 2, 1))

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onMarkDoneItemClick(reminder: Reminder, view: View) {
        viewModel.updateReminder(reminder)
    }

    override fun onResume() {
        super.onResume()
        when (reminderStatus) {
            ReminderStatus.TODAY -> viewModel.getTodayReminders(DateTimeUtils.getCurrentDate())
            else -> viewModel.getUpcomingReminders(DateTimeUtils.getCurrentDate())
        }
    }
}