package com.scurab.dipho.common.api

import com.scurab.dipho.common.model.ChatItems
import com.scurab.dipho.common.model.ChatRooms
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class ServerApi(
    private val config: ApiConfig,
    private val httpClient: HttpClient
) : IServerApi {

    override suspend fun getChatRooms(nextPageId: String?): ChatRooms = httpClient.get<String>(config.chatRooms(nextPageId))
        .let { Json.decodeFromString(it) }

    override suspend fun getMessages(chatRoomId: String): ChatItems = httpClient.get<String>(config.chatRoom(chatRoomId))
        .let { Json.decodeFromString(it) }
}