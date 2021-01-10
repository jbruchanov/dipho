package com.scurab.dipho.common.api

import com.scurab.dipho.common.model.ChatItems
import com.scurab.dipho.common.model.ChatRooms
import io.ktor.client.HttpClient
import io.ktor.client.request.get

class ServerApi(
    private val config: ApiConfig,
    private val httpClient: HttpClient
) : IServerApi {

    override suspend fun getChatRooms(nextPageId: String?): ChatRooms = httpClient.get(config.chatRooms(nextPageId))

    override suspend fun getMessages(chatRoomId: String): ChatItems = httpClient.get(config.chatRoom(chatRoomId))
}