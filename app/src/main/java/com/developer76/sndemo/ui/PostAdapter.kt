package com.developer76.sndemo.ui

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.developer76.sndemo.R
import com.developer76.sndemo.data.Post
import com.developer76.sndemo.inflate
import kotlinx.android.synthetic.main.item_post.view.*
import java.text.DecimalFormat

class PostAdapter(var posts: List<Post>, val listener: (Post) -> Unit) : RecyclerView.Adapter<PostAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(posts[position], listener)

    override fun getItemCount() = posts.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(parent!!.inflate(R.layout.item_post))

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Post, listener: (Post) -> Unit) = with(itemView) {
            post_header.text = item.title
            post_date.text = item.body

            setOnClickListener {
                listener(item)
            }
        }
    }
}