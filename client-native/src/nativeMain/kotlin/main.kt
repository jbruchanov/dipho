import com.scurab.dipho.common.IPlatform
import com.scurab.dipho.common.core.ILogger
import com.scurab.dipho.common.core.KtLogger
import com.scurab.dipho.common.coroutines.IDispatchers
import com.scurab.dipho.common.di.CommonModule
import com.scurab.dipho.common.model.ChatRooms
import com.scurab.dipho.common.usecase.LoadDataUseCase
import com.scurab.dipho.common.util.IDataFormatter
import com.scurab.dipho.home.HomeViewModel
import com.scurab.dipho.nav.INavigator
import io.ktor.client.HttpClient
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainCoroutineDispatcher
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import org.koin.core.context.KoinContextHandler
import org.koin.core.context.startKoin
import org.koin.dsl.module
import kotlin.coroutines.CoroutineContext

object NativeModule {
    val koinModule = module {
        single<ILogger> { KtLogger() }
        single<IDispatchers> { NativeDispatchers() }
        single<INavigator> { NativeNavigator() }
        single<IDataFormatter> { DataFormatter() }
        single<IPlatform> { Platform() }
//        single {
//            HttpClient(Curl) {
//                install(JsonFeature) {
//                    serializer = KotlinxSerializer()
//                }
//            }
//        }
    }
}

fun main() {
    val koin = startKoin {
        modules(CommonModule.koinModule)
        modules(NativeModule.koinModule)
    }

    val loadDataUseCase = KoinContextHandler.get().get<LoadDataUseCase>()
    val logger = KoinContextHandler.get().get<ILogger>()

    runBlocking {
        val loadChatRooms = loadDataUseCase.loadChatRooms()
        loadChatRooms.items.forEach {
            logger.d("I", it.toString())
        }
    }
}

class NativeDispatchers : IDispatchers {
    override val main: MainCoroutineDispatcher = Dispatchers.Main
    override val io: CoroutineContext = Dispatchers.Unconfined
    override val cpu: CoroutineContext = newSingleThreadContext("CPU")
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
