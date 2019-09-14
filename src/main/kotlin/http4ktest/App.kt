package http4ktest

import http4ktest.adapter.api.server
import http4ktest.domain.SomeService

class App

fun main(args: Array<String>) {
    val port: String = getEnv("PORT", "9000")
    val someService = SomeService()

    println("starting server on port $port")

    server(port.toInt(), someService).start()

    println("server started")
}

fun getEnv(key: String, default: String): String {
    val sysVal = System.getenv(key) ?: default
    return sysVal.ifBlank { default }
}
