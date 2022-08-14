package com.mohamedmabrouk.letsplant.util

import android.content.Context
import android.content.Intent
import com.mohamedmabrouk.letsplant.R

class ShareUtils {
    companion object {
        fun shareContent(context: Context, content: String) {
            val shareIntent = Intent()
            shareIntent.action = Intent.ACTION_SEND
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_TEXT, content)
            context.startActivity(
                Intent.createChooser(
                    shareIntent,
                    context.getString(R.string.send_to)
                )
            )
        }
    }
}