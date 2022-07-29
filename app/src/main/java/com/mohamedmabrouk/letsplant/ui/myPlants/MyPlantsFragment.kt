package com.mohamedmabrouk.letsplant.ui.myPlants

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.mohamedmabrouk.letsplant.R
import com.mohamedmabrouk.letsplant.databinding.FragmentMyPlantsBinding

class MyPlantsFragment : Fragment() {

    companion object {
        fun newInstance() = MyPlantsFragment()
    }

    private var _binding: FragmentMyPlantsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var viewModel: MyPlantsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyPlantsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MyPlantsViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Modify Header Title
        _binding?.pageTitle?.tvTitle!!.text = getString(R.string.my_plants)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}