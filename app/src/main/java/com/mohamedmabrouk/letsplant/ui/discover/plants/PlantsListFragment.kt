package com.mohamedmabrouk.letsplant.ui.discover.plants

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.mohamedmabrouk.letsplant.R
import com.mohamedmabrouk.letsplant.data.Plant
import com.mohamedmabrouk.letsplant.databinding.FragmentPlantsListBinding
import com.mohamedmabrouk.letsplant.util.Constants
import com.mohamedmabrouk.letsplant.util.LocaleHelper
import com.mohamedmabrouk.letsplant.util.hideLoadingIndicator
import com.mohamedmabrouk.letsplant.util.showLoadingIndicator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlantsListFragment : Fragment(), PlantItemsAdapter.PlantItemsClickListener {

    companion object {
        fun newInstance() = PlantsListFragment()
    }

    private var _binding: FragmentPlantsListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: PlantsListViewModel by activityViewModels()
    private var PLANTS_TYPE: Int = 0


    private lateinit var SELECTED_LANGUAGE_CODE : String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPlantsListBinding.inflate(inflater, container, false)
        PLANTS_TYPE = arguments?.getInt(Constants.PLANTS_TYPE) ?: 0

        SELECTED_LANGUAGE_CODE = LocaleHelper(activity as Context).getSelectedLanguageName()
        return binding.root
    }

//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
////        viewModel = ViewModelProvider(this).get(PlantsListViewModel::class.java)
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Modify Header Title
        _binding?.pageTitle?.tvTitle!!.text = when (PLANTS_TYPE) {
            0 -> getString(R.string.indoor_plants)
            1 -> getString(R.string.outdoor_plants)
            else -> getString(R.string.indoor_plants)
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
                _binding?.rvPlants!!.layoutManager = GridLayoutManager(activity, 2)
                val adapter = PlantItemsAdapter(it, this)
                _binding?.rvPlants!!.adapter = adapter
                _binding?.rvPlants!!.visibility = View.VISIBLE
            }
        }

        viewModel.empty.observe(viewLifecycleOwner) { isEmpty ->
            if (!isEmpty)
                _binding?.errorContainer!!.root.visibility = View.GONE
            else {
                _binding?.errorContainer!!.tvTitle.text = getString(R.string.no_items)
                _binding?.errorContainer!!.btnRetry.setOnClickListener {
                    getPlantsList()
                }
                _binding?.errorContainer!!.root.visibility = View.VISIBLE
            }
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            if (error == null)
                _binding?.errorContainer!!.root.visibility = View.GONE
            else {
                _binding?.errorContainer!!.tvTitle.text = error
                _binding?.errorContainer!!.btnRetry.setOnClickListener {
                    getPlantsList()
                }
                _binding?.errorContainer!!.root.visibility = View.VISIBLE
            }
        }
    }

    override fun onResume() {
        super.onResume()
        getPlantsList()
    }

    private fun getPlantsList(){
        viewModel.getPlants(PLANTS_TYPE, SELECTED_LANGUAGE_CODE)
    }

    private fun openPlantDetails(plant: Plant, view: View) {
        val bundle = bundleOf("plant" to plant)
        Navigation.findNavController(view)
            .navigate(R.id.action_plants_list_dest_to_plant_details_dest, bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(plant: Plant, view: View) {
        openPlantDetails(plant, view)
    }
}