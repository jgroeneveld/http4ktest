package http4ktest.adapter.api

import http4ktest.domain.SomeService
import io.mockk.every
import io.mockk.mockk
import org.amshove.kluent.shouldEqual
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import kotlin.test.Test
import kotlin.test.assertEquals

class Lib_kluent_Test {
    val someService = mockk<SomeService>()

    val handler = RootHandler(someService)

    // kluent: https://github.com/MarkusAmshove/Kluent
    @Test
    fun kluent() {
        every { someService.doStuff() } returns "Mock Answer"
        // theoretically kluent also brings a mockito wrapper:
        // When calling someService.doStuff() itReturns "Mock Answer"

        val response = handler(Request(Method.GET, "/"))

        // raw
        response.status shouldEqual Status.OK
        response.bodyString() shouldEqual "{\"foo_bar\" : \"root!\", \"lorem_ipsum\" : \"Mock Answer\"}"
        response.header("Content-Type") shouldEqual "application/json"

        // custom matchers
        response shouldHaveStatus Status.OK
        response shouldHaveBody "{\"foo_bar\" : \"root!\", \"lorem_ipsum\" : \"Mock Answer\"}"
        response shouldHaveHeader ("Content-Type" to "application/json")
    }

    // ******************************** extensions ********************************
    infix fun Response.shouldHaveStatus(expectedStatus: Status): Response {
        return this.apply { assertEquals(expectedStatus, this.status, "Response:\n$this\n\nStatus") }
    }

    infix fun Response.shouldHaveBody(body: String): Response {
        return this.apply { assertEquals(body, this.bodyString(), "Response:\n$this\n\nBody") }
    }

    infix fun Response.shouldHaveHeader(header: Pair<String, String>): Response {
        return this.apply { assertEquals(header.second, this.header(header.first), "Response:\n$this\n\nHeader '${header.first}'") }
    }
}
