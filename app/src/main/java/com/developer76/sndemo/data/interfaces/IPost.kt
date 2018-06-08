package com.developer76.sndemo.data.interfaces

import com.developer76.sndemo.data.Post
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET

interface IPost
{
    @GET("/posts")
    fun getPosts() : Observable<Response<List<Post>>>
}