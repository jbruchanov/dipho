package com.scurab.dipho.common.android.core

import android.util.Log
import com.scurab.dipho.common.core.ILogger

class AndroidLogger : ILogger {
    override fun d(tag: String, msg: String) {
        Log.d(tag, msg)
    }

    override fun e(tag: String, msg: String) {
        Log.e(tag, msg)
    }
}