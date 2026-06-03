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
        private const val API_KEY = "YOUR_API_KEY"
    }
}
