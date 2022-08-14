package com.mohamedmabrouk.letsplant.ui.welcome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mohamedmabrouk.letsplant.R
import com.mohamedmabrouk.letsplant.databinding.FragmentLanguageBinding


class LanguageFragment : Fragment() {

    companion object {
        fun newInstance() = LanguageFragment()
    }

    private var _binding: FragmentLanguageBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentLanguageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding?.groupEn?.radioBtn?.isChecked = false
        _binding?.groupAr?.radioBtn?.isChecked = false
        // AR
        _binding?.groupAr?.languageImg?.setImageResource(R.drawable.ic_ar)
        _binding?.groupAr?.languageText?.text = "العربية"
        _binding?.groupAr?.llMain?.setOnClickListener {
            _binding?.groupEn?.radioBtn?.isChecked = false
            _binding?.groupAr?.radioBtn?.isChecked = true
            _binding?.btnContinue?.visibility = View.VISIBLE
        }

        // EN
        _binding?.groupEn?.languageImg?.setImageResource(R.drawable.ic_en)
        _binding?.groupEn?.languageText?.text = "English"
        _binding?.groupEn?.llMain?.setOnClickListener {
            _binding?.groupAr?.radioBtn?.isChecked = false
            _binding?.groupEn?.radioBtn?.isChecked = true
            _binding?.btnContinue?.visibility = View.VISIBLE
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}