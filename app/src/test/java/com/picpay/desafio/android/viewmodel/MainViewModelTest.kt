package com.picpay.desafio.android.viewmodel

import android.view.View
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.picpay.desafio.android.remote.model.User
import com.picpay.desafio.android.remote.repository.PicPayRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
class MainViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()
    private val repository: PicPayRepository = mockk()
    private val progressBarObserver: Observer<Int> = mockk(relaxed = true)
    private val recyclerViewObserver: Observer<Int> = mockk(relaxed = true)
    private val usersListObserver: Observer<List<User>> = mockk(relaxed = true)
    private val responseMessageObserver: Observer<String> = mockk(relaxed = true)

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

    }

    @After
    fun cleanUp() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    //Testa se a viewModel está conseguindo chamar o repository
    @Test
    fun whenViewModelGetUsers_itShouldCallRepository_toFetchUsersList(){
        coEvery { repository.getUsers() } returns userList

        instantiate().getUsers()

        coVerify {
            repository.getUsers()
        }
    }

    //Testes para quando a getUsers() é chamadada:
    @Test
    fun whenViewModelGetUsers_started_thenItShouldTurnProgressBarVisible(){
        coEvery { repository.getUsers() } returns userList

        instantiate().getUsers()

        coVerify {
            progressBarObserver.onChanged(View.VISIBLE)
        }
    }

    @Test
    fun whenViewModelGetUsers_started_thenItShould(){
        coEvery { repository.getUsers() } returns userList

        instantiate().getUsers()

        coVerify {
            recyclerViewObserver.onChanged(View.GONE)
        }
    }

    //Testes para quando a getUsers() retorna com sucesso
    @Test
    fun whenViewModelGetUsers_getSuccess_thenItShouldUpdateList(){
        coEvery { repository.getUsers() } returns userList

        instantiate().getUsers()

        coVerify {
            usersListObserver.onChanged(userList)
        }
    }

    @Test
    fun whenViewModelGetUsers_getSuccess_thenItShouldClearResponseMessage(){
        coEvery { repository.getUsers() } returns userList

        instantiate().getUsers()

        coVerify {
            responseMessageObserver.onChanged("")
        }
    }

    private fun instantiate(): MainViewModel{
        val viewModel = MainViewModel(repository)
        viewModel.progressBar.observeForever(progressBarObserver)
        viewModel.recyclerView.observeForever(recyclerViewObserver)
        viewModel.userList.observeForever(usersListObserver)
        viewModel.responseMessage.observeForever(responseMessageObserver)

        return viewModel
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


