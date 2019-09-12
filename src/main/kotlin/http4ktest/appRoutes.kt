package http4ktest

import org.http4k.core.Method
import org.http4k.routing.bind
import org.http4k.routing.routes

val appRoutes = routes(
    "/" bind Method.GET to rootHandler,
    "/data" bind Method.POST to dataHandler
)
