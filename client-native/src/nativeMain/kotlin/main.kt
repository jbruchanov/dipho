import com.scurab.dipho.common.IPlatform
import com.scurab.dipho.common.api.ApiConfig
import com.scurab.dipho.common.api.ServerApi
import com.scurab.dipho.common.core.ILogger
import com.scurab.dipho.common.core.KtLogger
import com.scurab.dipho.common.coroutines.IDispatchers
import com.scurab.dipho.common.repo.AppRepo
import com.scurab.dipho.common.usecase.LoadDataUseCase
import com.scurab.dipho.common.util.IDataFormatter
import com.scurab.dipho.nav.INavigator
import io.ktor.client.HttpClient
import io.ktor.client.engine.curl.Curl
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainCoroutineDispatcher
import kotlinx.coroutines.runBlocking
import org.koin.dsl.module
import kotlin.coroutines.CoroutineContext

object NativeModule {
    val koinModule = module {
        single<ILogger> { KtLogger() }
        single<IDispatchers> { NativeDispatchers() }
        single<INavigator> { NativeNavigator() }
        single<IDataFormatter> { DataFormatter() }
        single<IPlatform> { Platform() }
        single {
            HttpClient(Curl) {
                install(JsonFeature) {
                    serializer = KotlinxSerializer()
                }
            }
        }
    }
}

fun main() {
    //koin doesn't seem to be working atm with native
    val http = HttpClient(Curl) {
        install(JsonFeature) {
            serializer = KotlinxSerializer()
        }
    }
    val loadDataUseCase = LoadDataUseCase(ServerApi(ApiConfig(url = "http://127.0.0.1:8088/api"), http), AppRepo())
    val logger = KtLogger()
    runBlocking {
        val loadChatRooms = loadDataUseCase.loadChatRooms()
        loadChatRooms.items.forEach {
            logger.d("I", "[${it.author.name}] ${it.subject}")
        }
    }
}

class NativeDispatchers : IDispatchers {
    override val main: MainCoroutineDispatcher = Dispatchers.Main
    override val io: CoroutineContext = Dispatchers.Unconfined
    override val cpu: CoroutineContext = Dispatchers.Default
}

class NativeNavigator : INavigator {
    override fun back() {}
    override fun home() {}
    override fun thread(threadId: String) {}
}

class Platform : IPlatform {
    override val host: IPlatform.Host = IPlatform.Host.Desktop
    override val platform: IPlatform.Platform = IPlatform.Platform.Windows
}

class DataFormatter : IDataFormatter {
    override fun toLongDate(value: Long): String {
        return "Date:$value"
    }

    override fun toLongTime(value: Long): String {
        return "Time:$value"
    }
}
