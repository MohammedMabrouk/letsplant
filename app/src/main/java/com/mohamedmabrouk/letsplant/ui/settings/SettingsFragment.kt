package com.mohamedmabrouk.letsplant.ui.settings

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mohamedmabrouk.letsplant.BuildConfig
import com.mohamedmabrouk.letsplant.R
import com.mohamedmabrouk.letsplant.data.DevicePreferences
import com.mohamedmabrouk.letsplant.databinding.FragmentSettingsBinding
import com.mohamedmabrouk.letsplant.ui.home.HomeActivity
import com.mohamedmabrouk.letsplant.ui.welcome.WelcomeActivity
import com.mohamedmabrouk.letsplant.util.LocaleHelper
import com.mohamedmabrouk.letsplant.util.ui.DialogUtils
import com.mohamedmabrouk.letsplant.util.ui.LanguageSelectListener

class SettingsFragment : Fragment() {

    lateinit var localeHelper: LocaleHelper
    lateinit var devicePreferences: DevicePreferences

    companion object {
        fun newInstance() = SettingsFragment()
    }

    private var _binding: FragmentSettingsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        localeHelper = LocaleHelper(activity as Context)
        devicePreferences = DevicePreferences(activity as Context)
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Modify Header Title
        binding.pageTitle.tvTitle.text = getString(R.string.settings)

        val languageString = when(localeHelper.getSelectedLanguageName()){
            LocaleHelper.LANGUAGE_ARABIC -> getString(R.string.arabic)
            LocaleHelper.LANGUAGE_ENGLISH -> getString(R.string.english)
            else -> getString(R.string.english)
        }
        binding.itemLanguage.tvName.text = getString(
            R.string.language,
            languageString
        )
        binding.itemLanguage.ivIcon.setImageResource(R.drawable.ic_language)
        binding.itemLanguage.root.setOnClickListener {
            DialogUtils.getAlertDialogForLanguageSelect(
                activity as Context,
                getString(R.string.select_your_language),
                getString(R.string.confirm),
                getString(R.string.cancel),
                object : LanguageSelectListener{
                    override fun onLanguageSelected(language: String?) {
                        if(language != localeHelper.getSelectedLanguageName()){
                            localeHelper.setUsersLocale(language)
                            startActivity(Intent(activity, HomeActivity::class.java))
                            activity?.finish()
                        }
                    }
                }
            ).show()
        }

        binding.itemAppVersion.tvName.text = getString(R.string.app_version, BuildConfig.VERSION_NAME)
        binding.itemAppVersion.ivIcon.setImageResource(R.drawable.ic_outline_info)
        binding.itemAppVersion.ivArrow.visibility = View.INVISIBLE
        binding.itemAppVersion.root.setOnClickListener {}

        binding.btnLogout.setOnClickListener {
            DialogUtils.getAlertDialogForConfirmation(
                activity as Context,
                getString(R.string.logout_confirmation),
                R.drawable.ic_exit,
                getString(R.string.confirm),
                getString(R.string.cancel)
            ){
                devicePreferences.saveLoginState(false)
                startActivity(Intent(activity, WelcomeActivity::class.java))
                activity?.finish()
            }.show()
        }
    }

}