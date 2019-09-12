package http4ktest.adapter.api

import assertk.assertThat
import http4ktest.EndToEndTest
import http4ktest.domain.SomeService
import http4ktest.testsupport.isOk
import org.junit.Test

class ExampleEndToEndTest : EndToEndTest() {
    override val someService: SomeService = SomeService()

    @Test
    fun `this test shows we can test the full stack easily`() {
        val response = getRequest("/")

        assertThat(response).isOk()
    }
}
