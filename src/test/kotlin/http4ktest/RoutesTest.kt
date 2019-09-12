package http4ktest

import assertk.assertThat
import http4ktest.testsupport.hasBody
import http4ktest.testsupport.hasStatus
import http4ktest.testsupport.isOk
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Status
import kotlin.test.Test

class RoutesTest {
    @Test
    fun `test Root`() {
        val expectedJson = "{\"blub\":\"root!\",\"blab\":\"blub\"}"

        val response = rootHandler(Request(Method.GET, "/"))

        assertThat(response).isOk().hasBody(expectedJson)
    }

    @Test
    fun `test routes`() {
        assertThat(appRoutes(Request(Method.GET, "/"))).isOk()
        assertThat(appRoutes(Request(Method.GET, "/does_not_exist"))).hasStatus(Status.NOT_FOUND)
    }
}
