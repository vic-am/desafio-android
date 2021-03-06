package com.picpay.desafio.android.view.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.picpay.desafio.android.R
import com.picpay.desafio.android.databinding.ActivityMainBinding
import com.picpay.desafio.android.view.adapter.UserListAdapter
import com.picpay.desafio.android.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var adapter: UserListAdapter

    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViewBinding()
        configRecyclerView()
        callUsers()

    }

    private fun initViewBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        val root = binding.root
        setContentView(root)

        recyclerView = binding.recyclerView
        progressBar = binding.userListProgressBar
    }

    private fun configRecyclerView() {
        adapter = UserListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun callUsers() {
        progressBar.visibility = View.VISIBLE

        viewModel.getUsers()
        initObservers()
    }

    private fun initObservers() {
        viewModel.userList.observe(this, Observer {
            adapter.users = it
        })

        viewModel.progressBar.observe(this, Observer {
            progressBar.visibility = it
        })

        viewModel.recyclerView.observe(this, Observer {
            recyclerView.visibility = it
        })

        viewModel.responseMessage.observe(this, Observer {
            if (it != ""){
                notifyIfResponseFailure(it)
            }
        })
    }

    private fun notifyIfResponseFailure(message: String) {

        Toast.makeText(this@MainActivity, getString(R.string.error), Toast.LENGTH_LONG)
            .show()
        Log.i("erro de conexão", message)
    }

}
