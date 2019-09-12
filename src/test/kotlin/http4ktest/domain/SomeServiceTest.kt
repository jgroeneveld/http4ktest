package http4ktest.domain

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.Test

class SomeServiceTest {
    @Test
    fun  testDoStuff() {
        val result = SomeService().doStuff()

        assertThat(result).isEqualTo("Hello World!")
    }
}
