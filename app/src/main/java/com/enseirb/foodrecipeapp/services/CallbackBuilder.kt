package com.enseirb.foodrecipeapp.services

import okhttp3.*
import java.net.URL


class CallbackBuilder () {

    var client = OkHttpClient()

    fun GET(url: URL, callback: Callback): Call {
        val request = Request.Builder()
            .url(url)
            .build()

        val call = client.newCall(request)
        call.enqueue(callback)
        return call
    }

}