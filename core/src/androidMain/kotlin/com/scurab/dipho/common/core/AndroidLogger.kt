package com.scurab.dipho.common.core

import android.util.Log

class AndroidLogger : ILogger {
    override fun d(tag: String, msg: String) {
        Log.d(tag, msg)
    }

    override fun e(tag: String, msg: String) {
        Log.e(tag, msg)
    }
}