package com.scurab.dipho.common.core

import org.koin.core.context.KoinContextHandler

interface ILogger {
    fun d(tag: String, msg: String)
    fun e(tag: String, msg: String)
}

interface IHasLogger {
    val logger: ILogger

    class Delegate : IHasLogger {
        override val logger: ILogger by KoinContextHandler.get().inject()
    }
}