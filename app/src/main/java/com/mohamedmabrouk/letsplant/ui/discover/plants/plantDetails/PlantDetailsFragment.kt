package com.mohamedmabrouk.letsplant.ui.discover.plants.plantDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.mohamedmabrouk.letsplant.R
import com.mohamedmabrouk.letsplant.databinding.FragmentPlantDetailsBinding

class PlantDetailsFragment : Fragment() {

    companion object {
        fun newInstance() = PlantDetailsFragment()
    }

    private var _binding: FragmentPlantDetailsBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var viewModel: PlantDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPlantDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PlantDetailsViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
       // todo: handle plant info
        _binding?.layoutOverview!!.ivImg.setImageResource(R.drawable.ic_outline_info)
        _binding?.layoutLight!!.ivImg.setImageResource(R.drawable.light)
        _binding?.layoutWatering!!.ivImg.setImageResource(R.drawable.watering)
        _binding?.layoutTemp!!.ivImg.setImageResource(R.drawable.temp)
        _binding?.layoutSoil!!.ivImg.setImageResource(R.drawable.soil)
        _binding?.layoutPropagation!!.ivImg.setImageResource(R.drawable.flowers)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}