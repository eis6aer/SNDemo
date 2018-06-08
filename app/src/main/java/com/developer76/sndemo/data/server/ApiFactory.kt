package com.developer76.sndemo.data.server

import android.util.Log
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


/**
 * Created by Javier on 11/4/2018.
 */
class ApiFactory {
    companion object {
        val TAG = "ApiService"
        val CONN_TIMEOUT_MILLIS = 12000L
        val READ_TIMEOUT_MILLIS = 12000L

        fun getinstance() : Retrofit.Builder
        {
            Log.d(TAG,"getInstance() called")
            var clientBuilder = OkHttpClient().newBuilder()

            var interceptor = Interceptor { chain ->
                var request = chain.request()
                var response = chain.proceed(request)
                var tryOuts = 2

                while (!response.isSuccessful() && tryOuts < 3) {
                    Log.d(TAG, "intercept: timeout/connection failure, performing automatic retry ${(tryOuts + 1)}")
                    tryOuts++
                    response = chain.proceed(request);
                }

                response

            }

            var loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            clientBuilder.connectTimeout(CONN_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)
            clientBuilder.readTimeout(READ_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)

            clientBuilder.addInterceptor(loggingInterceptor)
            clientBuilder.addInterceptor(interceptor)

            Log.d(TAG, "getInstance() completed")

            val gson = GsonBuilder()
                    .setLenient().create()

            return Retrofit.Builder()
                    .baseUrl("https://jsonplaceholder.typicode.com/")
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(clientBuilder.build())
        }

        fun <T> getService(type: Class<T>) : T
        {
            Log.d(TAG, "getServiceInstance() called with: type = [$type]")
            var retrofit = getinstance()

            return retrofit.build().create(type)

        }
    }


}