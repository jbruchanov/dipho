package com.scurab.dipho.common.ext

val Boolean.sign: Int get() = if (this == true) 1 else -1