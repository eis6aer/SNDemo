package com.developer76.sndemo.ui

import android.app.ProgressDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.developer76.sndemo.R
import com.developer76.sndemo.toast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_post_details.*

class PostDetailsActivity : AppCompatActivity() {

    companion object {
        val POST_ID = "POST_ID"
        val POST_TITLE = "POST_TITLE"
        val TAG = this.javaClass.simpleName
    }


    val detailViewModel by lazy {
        DetailViewModel()
    }

    val progressDialog by lazy {
        ProgressDialog(this).apply {
            setMessage("Cargando...")
            setCancelable(false)
        }
    }

    var disposables = CompositeDisposable()

    var post_id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_details)

        supportActionBar?.elevation = 0f

        if (intent.extras != null)
        {
            post_id = intent.extras.getInt(POST_ID)
            title = intent.extras.getString(POST_TITLE)
            initRecylerView()
        } else
            finish()
    }

    fun initRecylerView()
    {
        var llm = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        comments_rv.layoutManager = llm
    }

    override fun onResume() {
        super.onResume()
         if (post_id != 0)
         {
             initBindings()
         }
    }

    fun initBindings()
    {
        progressDialog.show()
        disposables.add(detailViewModel.getComments(post_id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    res ->
                    progressDialog.dismiss()

                    swiperefresh.let {
                        it.isRefreshing = false
                    }

                    if (res.code() == 200)
                    {
                        res.body().let {
                            Log.e(TAG,"returned with ${it!!.size}")
                            comments_rv.adapter = CommentsAdapter(res.body()!!) {

                            }
                        }

                    }
                    else
                        toast("Error en la petición")
                }
        )
    }
}
