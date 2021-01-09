package com.scurab.dipho.common.arch

actual open class PlatformViewModel {
    protected actual open fun onCleared() {
        //let subclass do something useful
    }
}