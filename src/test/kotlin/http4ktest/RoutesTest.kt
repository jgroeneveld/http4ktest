package http4ktest

import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import kotlin.test.Test
import kotlin.test.assertEquals

class RoutesTest {
    @Test
    fun `test Root`() {
        val request = Request(Method.GET, "/")
        val expectedJson = "{\"blub\":\"root!\",\"blab\":\"blub\"}"
        val expected = Response(Status.OK).body(expectedJson)

        assertEquals(expected.body, rootHandler(request).body)
    }

    @Test
    fun `test routes`() {
        assertEquals(Status.OK, appRoutes(Request(Method.GET, "/")).status)
        assertEquals(Status.NOT_FOUND, appRoutes(Request(Method.GET, "/does_not_exist")).status)
    }
}
