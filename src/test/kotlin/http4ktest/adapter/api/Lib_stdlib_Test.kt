package http4ktest.adapter.api

import http4ktest.domain.SomeService
import io.mockk.every
import io.mockk.mockk
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class Lib_stdlib_Test {
    val someService = mockk<SomeService>()

    val handler = RootHandler(someService)

    // kluent: https://github.com/MarkusAmshove/Kluent
    @Test
    fun kluent() {
        every { someService.doStuff() } returns "Mock Answer"

        val response = handler(Request(Method.GET, "/"))

        // raw
        assertEquals(Status.OK, response.status)
        assertEquals("{\"foo_bar\" : \"root!\", \"lorem_ipsum\" : \"Mock Answer\"}", response.bodyString())
        assertEquals("application/json", response.header("Content-Type"))

        // custom matchers
        assertResponseStatus(Status.OK, response)
        assertResponseBody("{\"foo_bar\" : \"root!\", \"lorem_ipsum\" : \"Mock Answer\"}", response)
        assertResponseHeader("Content-Type", "application/json", response)
    }

    // ******************************** extensions ********************************
    fun assertResponseStatus(expected: Status, response: Response) {
        assertEquals(expected, response.status, "Response:\n$response\n\nStatus")
    }

    fun assertResponseBody(expected: String, response: Response) {
        assertEquals(expected, response.bodyString(), "Response:\n$response\n\nBody")
    }

    fun assertResponseHeader(key: String, expectedValue: String, response: Response) {
        assertEquals(expectedValue, response.header(key), "Response:\n$response\n\nHeader '$key'")
    }
}
