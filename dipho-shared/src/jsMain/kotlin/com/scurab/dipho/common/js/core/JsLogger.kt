package com.scurab.dipho.common.js.core

import com.scurab.dipho.common.core.ILogger

class JsLogger : ILogger {
    override fun d(tag: String, msg: String) {
        console.log("[$tag] $msg")
    }

    override fun e(tag: String, msg: String) {
        console.error("[$tag] $msg")
    }
}