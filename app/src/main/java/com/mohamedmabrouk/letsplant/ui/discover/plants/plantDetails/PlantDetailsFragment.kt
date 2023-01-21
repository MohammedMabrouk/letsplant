package com.mohamedmabrouk.letsplant.ui.discover.plants.plantDetails

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.mohamedmabrouk.letsplant.R
import com.mohamedmabrouk.letsplant.data.Plant
import com.mohamedmabrouk.letsplant.databinding.FragmentPlantDetailsBinding
import com.mohamedmabrouk.letsplant.util.ShareUtils
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlantDetailsFragment : Fragment() {

    companion object {
        fun newInstance() = PlantDetailsFragment()
    }

    private var _binding: FragmentPlantDetailsBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var viewModel: PlantDetailsViewModel

    private lateinit var plant: Plant

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPlantDetailsBinding.inflate(inflater, container, false)
        plant = arguments?.get("plant") as Plant
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PlantDetailsViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding?.ivBack!!.setOnClickListener { activity?.onBackPressed() }
        _binding?.ivShare!!.setOnClickListener {
            //todo: add content to store and plant detials
            ShareUtils.shareContent(activity as Context, "test content ")
        }

        _binding?.layoutOverview!!.ivImg.setImageResource(R.drawable.ic_outline_info)
        _binding?.layoutLight!!.ivImg.setImageResource(R.drawable.light)
        _binding?.layoutWatering!!.ivImg.setImageResource(R.drawable.watering)
        _binding?.layoutTemp!!.ivImg.setImageResource(R.drawable.temp)
        _binding?.layoutSoil!!.ivImg.setImageResource(R.drawable.soil)
        _binding?.layoutPropagation!!.ivImg.setImageResource(R.drawable.flowers)

        _binding?.layoutOverview!!.tvTitle.text = getString(R.string.plant_overview)
        _binding?.layoutLight!!.tvTitle.text = getString(R.string.light)
        _binding?.layoutWatering!!.tvTitle.text = getString(R.string.watering)
        _binding?.layoutTemp!!.tvTitle.text = getString(R.string.temperature)
        _binding?.layoutSoil!!.tvTitle.text = getString(R.string.soil)
        _binding?.layoutPropagation!!.tvTitle.text = getString(R.string.propagation)

        _binding?.tvPlantTitle!!.text = plant.name
        _binding?.layoutOverview!!.tvInfo.text = plant.overview
        _binding?.layoutLight!!.tvInfo.text = plant.light
        _binding?.layoutWatering!!.tvInfo.text = plant.watering
        _binding?.layoutTemp!!.tvInfo.text = plant.temprature
        _binding?.layoutSoil!!.tvInfo.text = plant.soil
        _binding?.layoutPropagation!!.tvInfo.text = plant.propagation

        Picasso.get()
            .load(plant.imgUrl)
            .placeholder(R.drawable.green_tea_placeholder)
            .into(_binding?.ivPlantImg)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}