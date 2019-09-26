package http4ktest.adapter.api

import assertk.assertThat
import assertk.assertions.isEqualTo
import http4ktest.domain.SomeService
import http4ktest.testsupport.hasBody
import http4ktest.testsupport.isOk
import io.mockk.every
import io.mockk.mockk
import org.http4k.core.Method
import org.http4k.core.Request
import kotlin.test.Test

class RootHandlerTest {
    val someService = mockk<SomeService>()

    val handler = RootHandler(someService)

    @Test
    fun `this test shows we can easily test a single handler`() {
        every { someService.doStuff() } returns "Mock Answer"

        val expectedJson = "{\"foo_bar\" : \"root!\", \"lorem_ipsum\" : \"Mock Answer\"}"

        val response = handler(Request(Method.GET, "/"))

        assertThat(response).isOk().hasBody(expectedJson)
        assertThat(response.header("Content-Type")).isEqualTo("application/json")
    }
}
