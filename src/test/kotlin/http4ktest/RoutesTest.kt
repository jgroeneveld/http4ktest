package http4ktest

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import kotlin.test.Test

class RoutesTest {
    @Test
    fun `test Root`() {
        val request = Request(Method.GET, "/")
        val expectedJson = "{\"blub\":\"root!\",\"blab\":\"blub\"}"
        val expected = Response(Status.OK).body(expectedJson)

        assertThat(expected.body).isEqualTo(rootHandler(request).body)
    }

    @Test
    fun `test routes`() {
        assertThat(Status.OK).isEqualTo(appRoutes(Request(Method.GET, "/")).status)
        assertThat(Status.NOT_FOUND).isEqualTo(appRoutes(Request(Method.GET, "/does_not_exist")).status)
    }
}
