package com.developer76.sndemo.data.interfaces

import com.developer76.sndemo.data.Comment
import com.developer76.sndemo.data.Post
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface IPost
{
    @GET("/posts")
    fun getPosts() : Observable<Response<List<Post>>>

    @GET("/comments")
    fun getComments(@Query("postId") postId : Int) : Observable<Response<List<Comment>>>
}