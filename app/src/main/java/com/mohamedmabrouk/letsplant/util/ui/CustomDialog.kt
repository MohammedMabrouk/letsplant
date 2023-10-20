package com.mohamedmabrouk.letsplant.util.ui

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import com.mohamedmabrouk.letsplant.R

class CustomDialog(context: Context, layoutResourceId: Int?) : Dialog(context) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.round_dialog_layout)
//        yes = (Button) findViewById(R.id.btn_yes)
//        no = (Button) findViewById(R.id.btn_no)
//        yes.setOnClickListener(this)
//        no.setOnClickListener(this);
    }
}