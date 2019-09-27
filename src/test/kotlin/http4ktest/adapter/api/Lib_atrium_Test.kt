package http4ktest.adapter.api

import ch.tutteli.atrium.api.fluent.en_GB.toBe
import ch.tutteli.atrium.api.verbs.expect
import ch.tutteli.atrium.creating.Expect
import http4ktest.domain.SomeService
import io.mockk.every
import io.mockk.mockk
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import kotlin.test.Test

class Lib_atrium_Test {
    val someService = mockk<SomeService>()

    val handler = RootHandler(someService)

    // atrium: https://github.com/robstoll/atrium
    @Test
    fun atrium() {
        every { someService.doStuff() } returns "Mock Answer"

        val response = handler(Request(Method.GET, "/"))

        // raw
        expect(response.status).toBe(Status.OK)
        expect(response.bodyString()).toBe("{\"foo_bar\" : \"root!\", \"lorem_ipsum\" : \"Mock Answer\"}")
        expect(response.header("Content-Type")).toBe("application/json")

        // custom matchers
        expect(response).hasStatusOK()
        expect(response).hasBody("{\"foo_bar\" : \"root!\", \"lorem_ipsum\" : \"Mock Answer\"}")
        expect(response).hasHeader("Content-Type", "application/json")

        // custom matchers and block body to run all of them together
        expect(response) {
            hasStatusOK()
            hasBody("{\"foo_bar\" : \"root!\", \"lorem_ipsum\" : \"Mock Answer\"}")
            hasHeader("Content-Type", "application/json")
        }
    }

    // ******************************** extensions ********************************
    fun Expect<Response>.hasStatusOK(): Expect<Response> {
        return createAndAddAssertion("has status", Status.OK.toString()) {
            it.status == Status.OK
        }
    }

    fun Expect<Response>.hasBody(expectedBody: String): Expect<Response> {
        return createAndAddAssertion("has body", expectedBody) {
            it.bodyString() == expectedBody
        }
    }

    fun Expect<Response>.hasHeader(key: String, value: String): Expect<Response> {
        return createAndAddAssertion("has header", "$key: $value") {
            it.header(key) == value
        }
    }
}
