package http4ktest

import assertk.assertThat
import http4ktest.testsupport.isOk
import org.junit.Test

class ExampleEndToEndTest : EndToEndTest() {

    @Test
    fun execute() {
        val response = getRequest("/")

        assertThat(response).isOk()
    }
}
