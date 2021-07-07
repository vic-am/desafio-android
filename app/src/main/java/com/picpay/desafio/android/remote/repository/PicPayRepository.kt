package com.picpay.desafio.android.remote.repository

import com.picpay.desafio.android.remote.model.User
import com.picpay.desafio.android.remote.service.PicPayService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PicPayRepository(private val service: PicPayService): DefaultRepository {

    override suspend fun getUsers() =
        withContext(Dispatchers.IO) {
            return@withContext service.getUsers()
        }

}