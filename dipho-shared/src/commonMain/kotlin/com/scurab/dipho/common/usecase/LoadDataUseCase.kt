package com.scurab.dipho.common.usecase

import com.scurab.dipho.common.api.IServerApi
import com.scurab.dipho.common.model.ChatRooms
import com.scurab.dipho.common.repo.AppRepo

class LoadDataUseCase(
    private val api: IServerApi,
    private val repo: AppRepo
) {

    suspend fun loadChatRooms(): ChatRooms {
        val data = api.getChatRooms()
        repo.updateChatRooms(data)
        return data
    }
}