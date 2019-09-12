package http4ktest

import org.http4k.client.OkHttp
import org.http4k.core.Method
import org.http4k.core.Status
import org.junit.Test
import kotlin.test.assertEquals


class EndToEndTest {
    val testServer = server(0)
    val client = OkHttp()

    @Test
    fun execute() {
        testServer.start()

        val response = client(org.http4k.core.Request(
            Method.GET,
            "http://localhost:${testServer.port()}/"
        ))

        assertEquals(response.status, Status.OK)

        testServer.stop()
    }
}