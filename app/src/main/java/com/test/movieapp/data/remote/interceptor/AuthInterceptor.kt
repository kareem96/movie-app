package com.test.movieapp.data.remote.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url

        val urlWithApiKey = originalUrl.newBuilder()
            .addQueryParameter("api_key", API_KEY)
            .build()

        val newRequest = originalRequest.newBuilder()
            .url(urlWithApiKey)
            .build()

        return chain.proceed(newRequest)
    }

    companion object {
        private const val API_KEY = "0be47f8a233f2718d99d0c366369f1f8"
    }
}
