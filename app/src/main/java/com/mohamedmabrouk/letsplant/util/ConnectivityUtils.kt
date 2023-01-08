package com.mohamedmabrouk.letsplant.util

import android.content.Context
import android.net.ConnectivityManager


class ConnectivityUtils {
    companion object {
        // check for the internet using socket to ping google host
//    private fun isOnline(): Boolean {
//        return try {
//            runBlocking { withContext(Dispatchers.IO){
//                val timeoutMs = 6000
//                val sock = Socket()
//                sock.connect(InetSocketAddress(Constants.GOOGLE_HOST, Constants.GOOGLE_PORT), timeoutMs)
//                sock.close()
//                true
//            } }
//        } catch (e: IOException) {
//            false
//        }
//    }

        fun isNetworkAvailable(context: Context): Boolean {
            if ((context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo == null) {
                return false
            }
            return true
        }
    }
}