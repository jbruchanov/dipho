package com.scurab.dipho.common.arch

import androidx.lifecycle.ViewModel

actual open class PlatformViewModel : ViewModel() {

    actual override fun onCleared() {
        super.onCleared()
    }
}