package com.mohamedmabrouk.letsplant.ui.discover

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
import com.mohamedmabrouk.letsplant.data.Discover
import com.mohamedmabrouk.letsplant.databinding.FragmentDiscoverBinding
import com.mohamedmabrouk.letsplant.util.Constants

class DiscoverFragment : Fragment(), DiscoverItemsAdapter.DiscoverItemClickListener {

    companion object {
        fun newInstance() = DiscoverFragment()
    }

    private var _binding: FragmentDiscoverBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var viewModel: DiscoverViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDiscoverBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DiscoverViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Modify Header Title
        _binding?.pageTitle?.tvTitle!!.text = getString(R.string.discover)
        _binding?.rvDiscover!!.layoutManager = GridLayoutManager(activity, 2)

        val data = ArrayList<Discover>()
        data.add(
            Discover(
                R.drawable.indoor,
                getString(R.string.indoor_plants),
                Constants.DiscoverItem.INDOOR_PLANTS
            )
        )
        data.add(
            Discover(
                R.drawable.outdoor,
                getString(R.string.outdoor_plants),
                Constants.DiscoverItem.OUTDOOR_PLANTS
            )
        )
        data.add(
            Discover(
                R.drawable.care,
                getString(R.string.plants_care),
                Constants.DiscoverItem.PLANTS_CARE
            )
        )

        val adapter = DiscoverItemsAdapter(data, this)
        _binding?.rvDiscover!!.adapter = adapter
    }

    private fun navigate(itemType: Constants.DiscoverItem, view: View) {
        when (itemType) {
            Constants.DiscoverItem.INDOOR_PLANTS -> {
                val bundle = bundleOf(Constants.PLANTS_TYPE to 0)
                Navigation.findNavController(view)
                    .navigate(R.id.action_discover_dest_to_plants_list_dest, bundle)
            }
            Constants.DiscoverItem.OUTDOOR_PLANTS -> {
                val bundle = bundleOf(Constants.PLANTS_TYPE to 1)
                Navigation.findNavController(view)
                    .navigate(R.id.action_discover_dest_to_plants_list_dest, bundle)
            }
            Constants.DiscoverItem.PLANTS_CARE -> {
                val bundle = bundleOf(Constants.ARTICLES_TYPE to 0)
                Navigation.findNavController(view)
                    .navigate(R.id.action_discover_dest_to_articles_dest, bundle)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(itemType: Constants.DiscoverItem, view: View) {
        navigate(itemType, view)
    }
}