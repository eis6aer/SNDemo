package com.developer76.sndemo.ui

import com.developer76.sndemo.data.Post
import com.developer76.sndemo.data.interfaces.IPost
import com.developer76.sndemo.data.server.ApiFactory
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

class MainViewModel {

    val apiService by lazy {
        ApiFactory.getService(IPost::class.java)
    }

    fun getPosts() : Observable<Response<List<Post>>>
    {
        return apiService.getPosts()
                .subscribeOn(Schedulers.io())
    }
}