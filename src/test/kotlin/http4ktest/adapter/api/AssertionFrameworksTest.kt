package http4ktest.adapter.api

import assertk.Assert
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.prop
import ch.tutteli.atrium.api.fluent.en_GB.toBe
import ch.tutteli.atrium.api.verbs.expect
import ch.tutteli.atrium.creating.Expect
import http4ktest.domain.SomeService
import io.mockk.every
import io.mockk.mockk
import org.amshove.kluent.shouldEqual
import org.http4k.core.Body
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import kotlin.test.Test
import kotlin.test.assertEquals

class AssertionFrameworksTest {
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

    // ******************************** assertk extensions ********************************
    fun Assert<Response>.hasStatusOK(): Assert<Response> = this.hasStatus(Status.OK)

    fun Assert<Response>.hasStatus(status: Status): Assert<Response> {
        prop("status", Response::status).isEqualTo(status)
        return this
    }

    fun Assert<Response>.hasBody(body: String): Assert<Response> {
        prop("body", Response::body).isEqualTo(Body(body))
        return this
    }

    // ******************************** atrium extensions ********************************
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

    // ******************************** kluent extensions ********************************
    infix fun Response.shouldHaveStatus(expectedStatus: Status): Response {
        return this.apply { assertEquals(expectedStatus, this.status, "Response Status") }
    }

    infix fun Response.shouldHaveBody(body: String): Response {
        return this.apply { assertEquals(body, this.bodyString(), "Response Body") }
    }

    infix fun Response.shouldHaveHeader(header: Pair<String, String>): Response {
        return this.apply { assertEquals(header.second, this.header(header.first), "Response Header ${header.first}") }
    }
}
