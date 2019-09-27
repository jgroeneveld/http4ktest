package http4ktest.adapter.api

import assertk.assertThat
import http4ktest.EndToEndTest
import http4ktest.testsupport.hasStatusOK
import org.junit.Test

class ExampleEndToEndTest : EndToEndTest() {
    @Test
    fun `this test shows we can test the full stack easily`() {
        val response = getRequest("/")

        assertThat(response).hasStatusOK()
    }
}
