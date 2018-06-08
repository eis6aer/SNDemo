package com.developer76.sndemo.ui

import android.app.ProgressDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.developer76.sndemo.R
import com.developer76.sndemo.data.interfaces.IPost
import com.developer76.sndemo.data.server.ApiFactory
import com.developer76.sndemo.goToActivity
import com.developer76.sndemo.toast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        val TAG = this.javaClass.simpleName
    }

    val progressDialog by lazy {
        ProgressDialog(this).apply {
            setMessage("Cargando...")
            setCancelable(false)
        }
    }

    val mainViewModel by lazy {
        MainViewModel()
    }

    var disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.elevation = 0f

        initRecylerView()
        title = "Posts"

        swiperefresh.setOnRefreshListener {
            initBindings()
        }
    }

    fun initRecylerView()
    {
        var llm = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        posts_rv.layoutManager = llm
    }

    override fun onResume() {
        super.onResume()
        initBindings()
    }

    fun initBindings()
    {
        progressDialog.show()
        disposables.add(mainViewModel.getPosts()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe (
                        {
                            res ->
                                progressDialog.dismiss()

                                swiperefresh.let {
                                    it.isRefreshing = false
                                }

                                if (res.code() == 200)
                                {
                                    res.body().let {
                                        Log.e(TAG,"returned with ${it!!.size}")
                                        posts_rv.adapter = PostAdapter(res.body()!!) {
                                            var detailsIntent = Intent(this, PostDetailsActivity::class.java)
                                            detailsIntent.putExtra(PostDetailsActivity.POST_ID, it.id)
                                            detailsIntent.putExtra(PostDetailsActivity.POST_TITLE,it.title)
                                            startActivity(detailsIntent)
                                        }
                                    }

                                }
                                else
                                    toast("Error en la petición")
                        },{ t ->
                    toast("Error: ${t.localizedMessage}")
                    progressDialog.dismiss()
                }))
    }

    override fun onPause() {
        disposables.clear()
        super.onPause()
    }
}
