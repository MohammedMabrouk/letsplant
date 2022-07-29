package com.mohamedmabrouk.letsplant.ui.discover.plants

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.mohamedmabrouk.letsplant.R
import com.mohamedmabrouk.letsplant.databinding.FragmentPlantsListBinding
import com.mohamedmabrouk.letsplant.util.Constants

class PlantsListFragment : Fragment(), PlantItemsAdapter.PlantItemsClickListener {

    companion object {
        fun newInstance() = PlantsListFragment()
    }

    private var _binding: FragmentPlantsListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var viewModel: PlantsListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPlantsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PlantsListViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Modify Header Title
        _binding?.pageTitle?.tvTitle!!.text = when(arguments?.getInt(Constants.PLANTS_TYPE)){
            0 -> getString(R.string.indoor_plants)
            1 -> getString(R.string.outdoor_plants)
            else -> getString(R.string.indoor_plants)
        }

        _binding?.rvPlants!!.layoutManager = GridLayoutManager(activity, 2)

        val data = ArrayList<PlantItemModel>()
        for(i in 1..10){
            data.add(PlantItemModel(R.drawable.indoor, "plant $i"))
        }

        val adapter = PlantItemsAdapter(data, this)
        _binding?.rvPlants!!.adapter = adapter
    }

    private fun openPlantDetails(position: Int, view: View){
        // todo: send whole plant object
        val bundle = bundleOf("position" to 0)
        Navigation.findNavController(view)
            .navigate(R.id.action_plants_list_dest_to_plant_details_dest, bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(position: Int, view: View) {
        openPlantDetails(position, view)
    }
}