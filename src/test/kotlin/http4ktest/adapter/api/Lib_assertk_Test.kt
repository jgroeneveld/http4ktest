package http4ktest.adapter.api

import assertk.Assert
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.prop
import http4ktest.domain.SomeService
import io.mockk.every
import io.mockk.mockk
import org.http4k.core.Body
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import kotlin.test.Test

class Lib_assertk_Test {
    val someService = mockk<SomeService>()

    val handler = RootHandler(someService)

    // assertk https://github.com/willowtreeapps/assertk
    @Test
    fun assertk() {
        every { someService.doStuff() } returns "Mock Answer"

        val response = handler(Request(Method.GET, "/"))

        // raw
        assertThat(response.status).isEqualTo(Status.OK)
        assertThat(response.bodyString()).isEqualTo("{\"foo_bar\" : \"root!\", \"lorem_ipsum\" : \"Mock Answer\"}")
        assertThat(response.header("Content-Type")).isEqualTo("application/json")

        // custom matchers
        assertThat(response)
            .hasStatusOK()
            .hasBody("{\"foo_bar\" : \"root!\", \"lorem_ipsum\" : \"Mock Answer\"}")
    }

    // ******************************** extensions ********************************
    fun Assert<Response>.hasStatusOK(): Assert<Response> = this.hasStatus(Status.OK)

    fun Assert<Response>.hasStatus(status: Status): Assert<Response> {
        prop("status", Response::status).isEqualTo(status)
        return this
    }

    fun Assert<Response>.hasBody(body: String): Assert<Response> {
        prop("body", Response::body).isEqualTo(Body(body))
        return this
    }
}
