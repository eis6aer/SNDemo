package com.developer76.sndemo

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast


fun ViewGroup.inflate(layout: Int) = LayoutInflater.from(context).inflate(layout, this, false)

fun Activity.toast(message: String)
{
    Toast.makeText(this,message, Toast.LENGTH_SHORT).show()
}

fun View.toast(message: String)
{
    Toast.makeText(context,message, Toast.LENGTH_SHORT).show()
}
