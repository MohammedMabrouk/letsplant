package com.mohamedmabrouk.letsplant.ui.welcome

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mohamedmabrouk.letsplant.R
import com.mohamedmabrouk.letsplant.databinding.FragmentLanguageBinding
import com.mohamedmabrouk.letsplant.util.LocaleHelper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LanguageFragment : Fragment() {

    lateinit var localeHelper: LocaleHelper

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
        localeHelper = LocaleHelper(activity as Context)
        _binding = FragmentLanguageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // AR
        binding.groupAr.languageImg.setImageResource(R.drawable.ic_ar)
        binding.groupAr.languageText.text = "العربية"
        binding.groupAr.llMain.setOnClickListener {
            binding.groupEn.radioBtn.isChecked = false
            binding.groupAr.radioBtn.isChecked = true
            binding.btnContinue.visibility = View.VISIBLE
            updateLanguage(LocaleHelper.LANGUAGE_ARABIC)
        }

        // EN
        binding.groupEn.languageImg.setImageResource(R.drawable.ic_en)
        binding.groupEn.languageText.text = "English"
        binding.groupEn.llMain.setOnClickListener {
            binding.groupAr.radioBtn.isChecked = false
            binding.groupEn.radioBtn.isChecked = true
            binding.btnContinue.visibility = View.VISIBLE
            updateLanguage(LocaleHelper.LANGUAGE_ENGLISH)
        }

        binding.btnContinue.setOnClickListener {
            findNavController().navigate(R.id.action_languageFragment_to_loginFragment)
        }

    }

    override fun onResume() {
        super.onResume()

        if (localeHelper.isLangSelected()) {
            when (localeHelper.getSelectedLanguageName()) {
                LocaleHelper.LANGUAGE_ENGLISH -> binding.groupEn.llMain.performClick()
                LocaleHelper.LANGUAGE_ARABIC -> binding.groupAr.llMain.performClick()
            }
        } else {
            binding.groupEn.radioBtn.isChecked = false
            binding.groupAr.radioBtn.isChecked = false
        }
    }

    private fun updateLanguage(language: String) {
        if (language != localeHelper.getSelectedLanguageName()) {
            localeHelper.setUsersLocale(language)
            startActivity(Intent(activity, WelcomeActivity::class.java))
            activity?.finish()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}