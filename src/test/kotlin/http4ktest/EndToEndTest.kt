package http4ktest

import http4ktest.adapter.api.server
import http4ktest.domain.SomeService
import org.http4k.client.OkHttp
import org.http4k.core.Method
import org.http4k.core.Response
import org.junit.After
import org.junit.Before

abstract class EndToEndTest {
    abstract val someService: SomeService

    val testServer = server(0, someService)
    val client = OkHttp()

    @Before
    fun setup() {
        testServer.start()
    }

    @After
    fun teardown() {
        testServer.stop()
    }

    protected fun getRequest(path: String): Response {
        return client(
            org.http4k.core.Request(
                Method.GET, "http://localhost:${testServer.port()}$path"
            )
        )
    }
}
