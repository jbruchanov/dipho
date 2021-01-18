package com.scurab.dipho.common

interface IPlatform {
    val host: Host
    val platform: Platform

    enum class Host {
        Desktop, Mobile
    }

    enum class Platform {
        Js, Android, Ios, Windows, Linux
    }
}