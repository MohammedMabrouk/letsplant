package com.mohamedmabrouk.letsplant.ui.myPlants

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.mohamedmabrouk.letsplant.R
import com.mohamedmabrouk.letsplant.data.UserPlant
import com.mohamedmabrouk.letsplant.databinding.FragmentMyPlantsBinding
import com.mohamedmabrouk.letsplant.util.hideLoadingIndicator
import com.mohamedmabrouk.letsplant.util.showLoadingIndicator
import com.mohamedmabrouk.letsplant.util.ui.AddNewPlantListener
import com.mohamedmabrouk.letsplant.util.ui.DialogUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyPlantsFragment : Fragment(), MyPlantsAdapter.UserPlantClickListener {


    companion object {
        fun newInstance() = MyPlantsFragment()
    }

    private var _binding: FragmentMyPlantsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: MyPlantsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyPlantsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Modify Header Title
        binding.pageTitle.tvTitle.text = getString(R.string.my_plants)

        viewModel.getUserPlants()

        binding.fabAddPlant.setOnClickListener {
            DialogUtils.getAlertDialogForAddPlant(
                activity as Context,
                object : AddNewPlantListener {
                    override fun onNewPlantAdded(plant: UserPlant) {
                        viewModel.addNewPlant(plant)
                    }
                }
            ).show()
        }


        viewModel.dataLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading)
                showLoadingIndicator(this)
            else
                hideLoadingIndicator(this)
        }

        viewModel.items.observe(viewLifecycleOwner) {
            if (it == null)
                _binding?.rvPlants!!.visibility = View.INVISIBLE
            else {
                _binding?.rvPlants!!.layoutManager = LinearLayoutManager(activity)
                val adapter = MyPlantsAdapter(activity as Context, it, this)
                _binding?.rvPlants!!.adapter = adapter
                _binding?.rvPlants!!.visibility = View.VISIBLE
            }
        }

        viewModel.empty.observe(viewLifecycleOwner) { isEmpty ->
            if (!isEmpty)
                _binding?.ivEmpty!!.visibility = View.GONE
            else
                _binding?.ivEmpty!!.visibility = View.VISIBLE
        }

//        viewModel.error.observe(viewLifecycleOwner) { error ->
//            if (error == null)
//                _binding?.errorContainer!!.root.visibility = View.GONE
//            else {
//                _binding?.errorContainer!!.tvTitle.text = error
//                _binding?.errorContainer!!.btnRetry.setOnClickListener {
//                    getPlantsList()
//                }
//                _binding?.errorContainer!!.root.visibility = View.VISIBLE
//            }
//        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDeleteItemClick(plant: UserPlant, view: View) {
        viewModel.deletePlant(plant)
    }
}