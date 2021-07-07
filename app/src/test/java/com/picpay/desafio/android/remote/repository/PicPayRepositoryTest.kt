package com.picpay.desafio.android.remote.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.picpay.desafio.android.remote.model.User
import com.picpay.desafio.android.remote.service.PicPayService
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class PicPayRepositoryTest{

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()
    private val service: PicPayService = mockk()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

    }

    @After
    fun cleanUp() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun whenGetUsers_isCalled_thenItShouldCallPicPayService(){
        coEvery { service.getUsers() } returns userList

        runBlocking {
            PicPayRepository(service).getUsers()
        }

        coVerify { service.getUsers() }
    }

    private val userList = listOf(
        User(
            "https://randomuser.me/api/portraits/men/9.jpg",
            "Eduardo Santos",
            1001,
            "@eduardo.santos"
        ),
        User(
            "https://randomuser.me/api/portraits/men/9.jpg",
            "Eduardo Santos",
            1002,
            "@eduardo.santos"
        ),
        User(
            "https://randomuser.me/api/portraits/men/9.jpg",
            "Eduardo Santos",
            1003,
            "@eduardo.santos"
        )
    )
}