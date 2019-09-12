package http4ktest

import assertk.assertThat
import http4ktest.testsupport.isOk
import org.http4k.client.OkHttp
import org.http4k.core.Method
import org.junit.After
import org.junit.Before
import org.junit.Test

class EndToEndTest {
    val testServer = server(0)
    val client = OkHttp()

    @Before
    fun setup() {
        testServer.start()
    }

    @After
    fun teardown() {
        testServer.stop()
    }

    @Test
    fun execute() {
        val response = client(
            org.http4k.core.Request(
                Method.GET, "http://localhost:${testServer.port()}/"
            )
        )

        assertThat(response).isOk()
    }
}
