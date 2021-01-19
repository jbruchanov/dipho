object Versions {
    const val kotlin = "1.4.21"
    const val kotlinCoroutines = "1.4.2-native-mt"
    const val kotlinSerialisation = "1.0.1"
    const val ktor = "1.5.0"

    const val reactJs = "17.0.0-pre.134-kotlin-${kotlin}"
    //locally build koin due to missing mingwx64 version
    val koin = "3.0.0-alpha-4" + ("-SNAPSHOT".takeIf { isWin() } ?: "")

    const val androidAppCompat = "1.2.0"
    const val androidLifecycle = "2.2.0"
    const val androidFragment = "1.2.5"

    fun isWin() = System.getProperty("os.name")?.startsWith("Windows") ?: false
}