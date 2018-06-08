package com.developer76.sndemo.data

data class Post(var useriD : Int, var id: Int, var title : String, var body : String)
data class Comment(var postId : Int, var id : Int, var name : String, var email: String, var body : String)