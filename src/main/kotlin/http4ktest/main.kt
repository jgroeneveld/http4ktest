package http4ktest

import http4ktest.adapter.api.server
import http4ktest.domain.SomeService

fun main(args: Array<String>) {
    val someService = SomeService()

    server(9000, someService).start()

    println("server started")
}
