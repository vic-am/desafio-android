package com.picpay.desafio.android.viewmodel

import android.view.View
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.verify
import com.picpay.desafio.android.remote.model.User
import com.picpay.desafio.android.remote.repository.DefaultRepository
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    private lateinit var progressBarObserver: Observer<Int>

    @Mock
    private lateinit var recyclerViewObserver: Observer<Int>

    @Mock
    private lateinit var usersListObserver: Observer<List<User>>

    @Mock
    private lateinit var responseMessageObserver: Observer<String>


    private lateinit var viewModel: MainViewModel


}


