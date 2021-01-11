package com.scurab.dipho.common.util

interface IDataFormatter {
    fun toLongDate(value: Long): String
    fun toLongTime(value: Long): String
}