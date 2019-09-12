package http4ktest.adapter.api

import assertk.assertThat
import http4ktest.domain.SomeService
import http4ktest.testsupport.hasBody
import http4ktest.testsupport.isOk
import org.http4k.core.Method
import org.http4k.core.Request
import kotlin.test.Test

class RootHandlerTest {
    val someService = SomeService()

    val handler = rootHandler(someService)

    @Test
    fun `this test shows we can easily test a single handler`() {
        val expectedJson = "{\"blub\":\"root!\",\"blab\":\"blub\"}"

        val response = handler(Request(Method.GET, "/"))

        assertThat(response).isOk().hasBody(expectedJson)
    }
}
