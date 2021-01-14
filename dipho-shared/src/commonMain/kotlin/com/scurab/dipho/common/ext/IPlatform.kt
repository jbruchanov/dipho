package com.scurab.dipho.common.ext

import com.scurab.dipho.common.IPlatform

val IPlatform.showLinksAsButtons get() = this.host == IPlatform.Host.Mobile