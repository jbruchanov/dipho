package com.scurab.dipho.common.core

class KtLogger : ILogger {
    override fun d(tag: String, msg: String) {
        println("D[$tag] $msg")
    }

    override fun e(tag: String, msg: String) {
        println("E[$tag] $msg")
    }
}