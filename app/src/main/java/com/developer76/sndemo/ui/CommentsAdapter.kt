package com.developer76.sndemo.ui

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.developer76.sndemo.R
import com.developer76.sndemo.data.Comment
import com.developer76.sndemo.data.Post
import com.developer76.sndemo.inflate
import kotlinx.android.synthetic.main.item_comment.view.*
import kotlinx.android.synthetic.main.item_post.view.*

class CommentsAdapter(var comments: List<Comment>, val listener: (Comment) -> Unit) : RecyclerView.Adapter<CommentsAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(comments[position], listener)

    override fun getItemCount() = comments.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(parent!!.inflate(R.layout.item_comment))

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Comment, listener: (Comment) -> Unit) = with(itemView) {
            comments_header.text = item.name
            comments_email.text = item.email
            comments_body.text = item.body

            setOnClickListener {
                listener(item)
            }
        }
    }
}