package com.example.barcodetest.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.AsyncTask
import android.os.AsyncTask.execute
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket

internal class AppStatus(private val onInternetChecked: (Boolean) -> Unit) :

    AsyncTask<Void, Void, Boolean>() {
    init {
        execute()
    }

    override fun doInBackground(vararg voids: Void): Boolean {
        return try {
            val sock = Socket()
            sock.connect(InetSocketAddress("8.8.8.8", 53), 1500)
            sock.close()
            true
        } catch (e: IOException) {
            false
        }

    }

    override fun onPostExecute(internet: Boolean) {
        onInternetChecked(internet)
    }
}



