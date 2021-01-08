
expect class Platform() {
    fun name(): String
}

class PlatformGreetings {
    fun greetings() = "Hello from ${Platform().name()}"
}