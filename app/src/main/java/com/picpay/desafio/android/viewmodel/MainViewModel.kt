package com.picpay.desafio.android.viewmodel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.picpay.desafio.android.remote.model.User
import com.picpay.desafio.android.remote.repository.DefaultRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
        viewModelScope.launch {
            _progressBar.value = View.VISIBLE

            try {
                val users = withContext(Dispatchers.Default){
                    repository.getUsers()
                }
                _reponseMessage.value = ""
                _recyclerView.value = View.VISIBLE
                _progressBar.value = View.GONE
                _usersList.value = users            }
            catch (e: Exception){
                _reponseMessage.value = e.message
                _progressBar.value = View.GONE
                _recyclerView.value = View.GONE
            }
        }
    }

}