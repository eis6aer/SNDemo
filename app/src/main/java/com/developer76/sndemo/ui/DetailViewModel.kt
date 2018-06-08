package com.developer76.sndemo.ui

import com.developer76.sndemo.data.Comment
import com.developer76.sndemo.data.interfaces.IPost
import com.developer76.sndemo.data.server.ApiFactory
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

class DetailViewModel {
    val apiService by lazy {
        ApiFactory.getService(IPost::class.java)
    }

    fun getComments(postId : Int) : Observable<Response<List<Comment>>>
    {
        return apiService.getComments(postId)
                .subscribeOn(Schedulers.newThread())
    }
}