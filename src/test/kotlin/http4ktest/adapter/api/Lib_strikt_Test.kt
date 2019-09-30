package http4ktest.adapter.api

import http4ktest.domain.SomeService
import io.mockk.every
import io.mockk.mockk
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import strikt.api.Assertion
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import kotlin.test.Test

class Lib_strikt_Test {
    val someService = mockk<SomeService>()

    val handler = RootHandler(someService)

    // strikt: https://strikt.io/
    @Test
    fun kluent() {
        every { someService.doStuff() } returns "Mock Answer"

        val response = handler(Request(Method.GET, "/"))

        // raw
        expectThat(response.status).isEqualTo(Status.OK)
        expectThat(response.bodyString()).isEqualTo("{\"foo_bar\" : \"root!\", \"lorem_ipsum\" : \"Mock Answer\"}")
        expectThat(response.header("Content-Type")).isEqualTo("application/json")

        // raw with custom mappings
        expectThat(response).status.isEqualTo(Status.OK)
        expectThat(response).body.isEqualTo("{\"foo_bar\" : \"root!\", \"lorem_ipsum\" : \"Mock Answer\"}")
        expectThat(response).header("Content-Type").isEqualTo("application/json")

        // block assertions
        expectThat(response) {
            status.isEqualTo(Status.OK)
            body.isEqualTo("{\"foo_bar\" : \"root!\", \"lorem_ipsum\" : \"Mock Answer\"}")
            header("Content-Type").isEqualTo("application/json")
        }

        // custom matchers
        expectThat(response).hasStatus(Status.OK)
        expectThat(response).hasBody("{\"foo_bar\" : \"root!\", \"lorem_ipsum\" : \"Mock Answer\"}")
        expectThat(response).hasHeader("Content-Type", "application/json")

        // custom matchers with block assertions
        expectThat(response) {
            hasStatus(Status.OK)
            hasBody("{\"foo_bar\" : \"root!\", \"lorem_ipsum\" : \"Mock Answer\"}")
            hasHeader("Content-Type", "application/json")
        }
    }

    // ******************************** extensions ********************************
    fun Assertion.Builder<Response>.hasStatus(status: Status) = assert("has status %s", status) {
        when(it.status) {
            status -> pass()
            else -> fail(actual = it.status)
        }
    }

    fun Assertion.Builder<Response>.hasBody(body: String) = assert("has body %s", body) {
        when(it.bodyString()) {
            body -> pass()
            else -> fail(actual = it.bodyString())
        }
    }

    fun Assertion.Builder<Response>.hasHeader(name: String, value: String) = assert("has header %s", name) {
        val actual = it.header(name)
        when(actual) {
            value -> pass()
            else -> fail(actual = actual)
        }
    }

    // ******************************** custom mappings ********************************
    val Assertion.Builder<Response>.status: Assertion.Builder<Status>
        get() = get { status }

    val Assertion.Builder<Response>.body: Assertion.Builder<String>
        get() = get { bodyString() }

    fun Assertion.Builder<Response>.header(name: String): Assertion.Builder<String?> {
        return get { header(name) }
    }
}
