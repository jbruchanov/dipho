package com.scurab.dipho.common.core

import org.koin.core.context.KoinContextHandler

interface ILogger {
    fun d(tag: String, msg: String)
    fun e(tag: String, msg: String)
}

interface HasLogger {
    val logger: ILogger

    class Delegate : HasLogger {
        override val logger: ILogger by KoinContextHandler.get().inject()
    }
}