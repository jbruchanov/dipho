import com.scurab.dipho.common.model.Author
import react.dom.render
import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.css.Color
import kotlinx.css.backgroundColor
import react.dom.div
import styled.css
import styled.styledDiv

fun main() {
    println("AppStart#main")
    window.onload = {
        document.body!!.insertAdjacentHTML("afterbegin", "<div id='root'></div>")
        render(document.getElementById("root")) {
            styledDiv {
                css {
                    backgroundColor = Color.beige
                }
                val auth = Author("1", "Test")
                div {
                    +"${auth.id} - ${auth.name}"
                }
                div {
                    +PlatformGreetings().greetings()
                }
            }
        }
    }
}
