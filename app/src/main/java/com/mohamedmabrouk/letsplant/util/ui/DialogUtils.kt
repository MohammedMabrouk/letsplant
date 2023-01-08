package com.mohamedmabrouk.letsplant.util.ui

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.Spannable
import android.text.SpannableString
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.google.android.material.slider.Slider
import com.google.android.material.textfield.TextInputEditText
import com.mohamedmabrouk.letsplant.R
import com.mohamedmabrouk.letsplant.data.UserPlant
import com.mohamedmabrouk.letsplant.util.DateTimeUtils
import com.mohamedmabrouk.letsplant.util.LocaleHelper
import com.mohamedmabrouk.letsplant.util.NumbersUtility
import java.util.*


object DialogUtils {

    fun getAlertDialogForLanguageSelect(
        context: Context,
        title: CharSequence,
        positiveButtonText: String,
        negativeButtonText: String,
        languageSelectListener: LanguageSelectListener
    ): AlertDialog {
        val dialogBuilder = AlertDialog.Builder(context)
        val view =
            LayoutInflater.from(context).inflate(R.layout.language_select_dialog_layout, null)
        dialogBuilder.setView(view)
        val dialog = dialogBuilder.create()
        dialog.setCancelable(false)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val tvTitle = (view.findViewById(R.id.tv_title) as TextView)
        tvTitle.text = title

        val arContainer = (view.findViewById(R.id.group_ar) as LinearLayout)
        val arTextView = (arContainer.findViewById(R.id.language_text) as TextView)
        val arRadioButton = (arContainer.findViewById(R.id.radio_btn) as RadioButton)
        val arImageView = (arContainer.findViewById(R.id.language_img) as ImageView)

        arTextView.text = context.getString(R.string.arabic)
        arImageView.setImageResource(R.drawable.ic_ar)

        val enContainer = (view.findViewById(R.id.group_en) as LinearLayout)
        val enTextView = (enContainer.findViewById(R.id.language_text) as TextView)
        val enRadioButton = (enContainer.findViewById(R.id.radio_btn) as RadioButton)
        val enImageView = (enContainer.findViewById(R.id.language_img) as ImageView)

        enTextView.text = context.getString(R.string.english)
        enImageView.setImageResource(R.drawable.ic_en)

        var selectedLanguage : String? = null

        arContainer.setOnClickListener {
            arRadioButton.isChecked = true
            enRadioButton.isChecked = false
            selectedLanguage = LocaleHelper.LANGUAGE_ARABIC
        }

        enContainer.setOnClickListener {
            arRadioButton.isChecked = false
            enRadioButton.isChecked = true
            selectedLanguage = LocaleHelper.LANGUAGE_ENGLISH
        }

        val positiveButton = view.findViewById<Button>(R.id.btn_positive)
        positiveButton.text = positiveButtonText
        positiveButton.setOnClickListener {
            dialog.cancel()
            if(selectedLanguage != null)
            languageSelectListener.onLanguageSelected(selectedLanguage)
        }

        val negativeButton = view.findViewById<Button>(R.id.btn_negative)
        negativeButton.text = negativeButtonText
        negativeButton.setOnClickListener {
            dialog.cancel()
        }

        return dialog
    }

    fun getAlertDialogForConfirmation(
        context: Context,
        title: CharSequence,
        imageResourceId: Int,
        positiveButtonText: String,
        negativeButtonText: String,
        positiveButtonListener: Runnable
    ): AlertDialog {
        val dialogBuilder = AlertDialog.Builder(context)
        val view =
            LayoutInflater.from(context).inflate(R.layout.confirmation_dialog_layout, null)
        dialogBuilder.setView(view)
        val dialog = dialogBuilder.create()
        dialog.setCancelable(false)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val tvTitle = (view.findViewById(R.id.tv_title) as TextView)
        tvTitle.text = title

        val ivIndicator = (view.findViewById(R.id.iv_indicator) as ImageView)
        ivIndicator.setImageResource(imageResourceId)

        val positiveButton = view.findViewById<Button>(R.id.btn_positive)
        positiveButton.text = positiveButtonText
        positiveButton.setOnClickListener {
            dialog.cancel()
            positiveButtonListener.run()
        }

        val negativeButton = view.findViewById<Button>(R.id.btn_negative)
        negativeButton.text = negativeButtonText
        negativeButton.setOnClickListener {
            dialog.cancel()
        }

        return dialog
    }

    fun getAlertDialogForAddPlant(
        context: Context,
        addNewPlantListener: AddNewPlantListener
    ): AlertDialog {
        val dialogBuilder = AlertDialog.Builder(context)
        val view =
            LayoutInflater.from(context).inflate(R.layout.add_plant_dialog_layout, null)
        dialogBuilder.setView(view)
        val dialog = dialogBuilder.create()
        dialog.setCancelable(false)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val plantNameEditText = view.findViewById<TextInputEditText>(R.id.et_name)

        val wateringInfoTextView = view.findViewById<TextView>(R.id.tv_watering_title)
        val wateringSlider = view.findViewById<Slider>(R.id.slider_watering)

        var wateringNumberText1 = ""
        var wateringText1 = ""

        var spannable1: Spannable

        wateringSlider.addOnChangeListener { slider, value, fromUser ->
             wateringNumberText1 = NumbersUtility.getFormattedInteger(value.toInt().toString(), Locale(LocaleHelper(context).getSelectedLanguageName()))
             wateringText1 = context.getString(R.string.repeat_watering_title) +
                    wateringNumberText1 + context.getString(R.string.days)

            spannable1 = SpannableString(wateringText1)
            spannable1.setSpan(
                AbsoluteSizeSpan(20, true),
                context.getString(R.string.repeat_watering_title).length,
                (context.getString(R.string.repeat_watering_title) + wateringNumberText1).length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            spannable1.setSpan(
                ForegroundColorSpan(context.getColor(R.color.green_2)),
                context.getString(R.string.repeat_watering_title).length,
                (context.getString(R.string.repeat_watering_title) + wateringNumberText1).length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            wateringInfoTextView.setText(spannable1, TextView.BufferType.SPANNABLE)
        }
        // initial value
        wateringSlider.value = 1F

        val fertilizeInfoTextView = view.findViewById<TextView>(R.id.tv_fertilize_title)
        val fertilizeSlider = view.findViewById<Slider>(R.id.slider_fertilize)


        var fertilizeNumberText = ""
        var fertilizeText = ""

        var spannable2: Spannable

        fertilizeSlider.addOnChangeListener { slider, value, fromUser ->
            fertilizeNumberText= NumbersUtility.getFormattedInteger(value.toInt().toString(), Locale(LocaleHelper(context).getSelectedLanguageName()))
            fertilizeText = context.getString(R.string.repeat_fertilize_title) +
                    fertilizeNumberText + context.getString(R.string.days)

            spannable2 = SpannableString(fertilizeText)
            spannable2.setSpan(
                AbsoluteSizeSpan(20, true),
                context.getString(R.string.repeat_fertilize_title).length,
                (context.getString(R.string.repeat_fertilize_title) + fertilizeNumberText).length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            spannable2.setSpan(
                ForegroundColorSpan(context.getColor(R.color.green_2)),
                context.getString(R.string.repeat_fertilize_title).length,
                (context.getString(R.string.repeat_fertilize_title) + fertilizeNumberText).length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            fertilizeInfoTextView.setText(spannable2, TextView.BufferType.SPANNABLE)
        }
        // initial value
        fertilizeSlider.value = 1F

        val positiveButton = view.findViewById<Button>(R.id.btn_positive)
        positiveButton.setOnClickListener {
            dialog.cancel()
          addNewPlantListener.onNewPlantAdded(
              UserPlant(
                  null,
                  plantNameEditText?.text?.toString(),
                  wateringSlider.value.toInt(),
                  DateTimeUtils.getCurrentDate(),
                  DateTimeUtils.getDateWithAddedDays(wateringSlider.value.toInt()),
                  fertilizeSlider.value.toInt(),
                  DateTimeUtils.getCurrentDate(),
                  DateTimeUtils.getDateWithAddedDays(fertilizeSlider.value.toInt())
              )
          )
        }

        val negativeButton = view.findViewById<Button>(R.id.btn_negative)
        negativeButton.setOnClickListener {
            dialog.cancel()
        }

        return dialog
    }
}