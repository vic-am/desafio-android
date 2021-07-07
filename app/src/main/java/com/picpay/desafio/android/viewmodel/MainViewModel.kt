package com.picpay.desafio.android.viewmodel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.picpay.desafio.android.remote.model.User
import com.picpay.desafio.android.remote.repository.ApiListener
import com.picpay.desafio.android.remote.repository.DefaultRepository
import com.picpay.desafio.android.utls.Constants

class MainViewModel(private val repository: DefaultRepository) : ViewModel() {

    private val _progressBar = MutableLiveData<Int>()
    val progressBar: LiveData<Int> = _progressBar

    private val _recyclerView = MutableLiveData<Int>()
    val recyclerView: LiveData<Int> = _recyclerView

    private val _usersList = MutableLiveData<List<User>>()
    val userList: LiveData<List<User>> = _usersList

    private val _reponseMessage = MutableLiveData<String>()
    val responseMessage: LiveData<String> = _reponseMessage

    fun getUsers() {
        val listener = object : ApiListener<List<User>> {
            override fun onSuccess(list: List<User>) {
                _reponseMessage.value = ""
                _progressBar.value = View.GONE
                _usersList.value = list
            }

            override fun onFailure(message: String) {
                _reponseMessage.value = message
                _progressBar.value = View.GONE
                _recyclerView.value = View.GONE
            }

        }

        repository.getUsers(listener)
    }

}