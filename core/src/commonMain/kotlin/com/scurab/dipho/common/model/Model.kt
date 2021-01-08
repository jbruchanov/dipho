package com.scurab.dipho.common.model

data class Author(
    val id: String,
    val name: String
)

data class Thread(
    val id: String,
    val subject: String,
    val author: Author
)

data class Message(
    val message: String
)
