package com.picpay.desafio.android.remote.service

import com.picpay.desafio.android.remote.model.User
import retrofit2.http.GET


interface PicPayService {

    @GET("users")
    suspend fun getUsers(): List<User>

}