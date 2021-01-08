import com.scurab.dipho.common.di.JsModule
import com.scurab.dipho.web.App
import kotlinx.browser.document
import kotlinx.browser.window
import org.koin.core.context.startKoin
import react.dom.render

fun main() {
    initDi()
    window.onload = {
        document.body!!.insertAdjacentHTML("afterbegin", "<div id='root'></div>")

        render(document.getElementById("root")) {
            child(App::class) {
            }
        }
    }
}

fun initDi() {
    startKoin {
        modules(JsModule.koinModule)
    }
}