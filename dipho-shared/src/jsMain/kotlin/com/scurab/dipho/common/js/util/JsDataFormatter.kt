package com.scurab.dipho.common.js.util

import com.scurab.dipho.common.util.IDataFormatter
import kotlin.js.Date

class JsDataFormatter : IDataFormatter {

    private val locale: DateTimeConverters get() = UK

    override fun toLongDate(value: Long) = locale.run { Date(value).toLongDateString() }
    override fun toLongTime(value: Long) = locale.run { Date(value).toLongTimeString() }

    private interface DateTimeConverters {
        fun Date.toLongDateString(): String
        fun Date.toLongTimeString(): String
    }

    companion object {
        private val Int.d get() = if (this < 10) "0$this" else "$this"
        private val Date.day get() = getDate().d
        private val Date.month get() = (getMonth() + 1).d
        private val Date.year get() = getFullYear().d
        private val Date.hours get() = getHours().d
        private val Date.mins get() = getMinutes().d
        private val Date.secs get() = getSeconds().d

        private object CZ : DateTimeConverters {
            override fun Date.toLongDateString() = "$day.$month. $year"
            override fun Date.toLongTimeString() = "$hours:$mins:$secs"
        }

        private object UK : DateTimeConverters {
            override fun Date.toLongDateString() = "$day/$month/$year"
            override fun Date.toLongTimeString() = "$hours:$mins:$secs"
        }
    }
}