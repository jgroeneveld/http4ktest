package http4ktest.adapter.api

import assertk.Assert
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.prop
import ch.tutteli.atrium.api.verbs.expect
import ch.tutteli.atrium.creating.Expect
import http4ktest.domain.SomeService
import io.mockk.every
import io.mockk.mockk
import org.http4k.core.Body
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import kotlin.test.Test

class AssertionFrameworksTest {
    val someService = mockk<SomeService>()

    val handler = RootHandler(someService)

    @Test
    fun assertk() {
        every { someService.doStuff() } returns "Mock Answer"

        val response = handler(Request(Method.GET, "/"))

        assertThat(response).isOk().hasBody("{\"foo_bar\" : \"root!\", \"lorem_ipsum\" : \"Mock Answer\"}")
        assertThat(response.header("Content-Type")).isEqualTo("application/json")
    }

    @Test
    fun atrium() {
        every { someService.doStuff() } returns "Mock Answer"

        val response = handler(Request(Method.GET, "/"))

        expect(response) {
            hasStatusOk()
            hasBody("{\"foo_bar\" : \"root!\", \"lorem_ipsum\" : \"Mock Answer\"}")
            hasHeader("Content-Type", "application/json")
        }
    }

    // ******************************** assertk extensions ********************************
    fun Assert<Response>.isOk(): Assert<Response> = this.hasStatus(Status.OK)

    fun Assert<Response>.hasStatus(status: Status): Assert<Response> {
        prop("status", Response::status).isEqualTo(status)
        return this
    }

    fun Assert<Response>.hasBody(body: String): Assert<Response> {
        prop("body", Response::body).isEqualTo(Body(body))
        return this
    }

    // ******************************** atrium extensions ********************************
    fun Expect<Response>.hasStatusOk(): Expect<Response> {
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
