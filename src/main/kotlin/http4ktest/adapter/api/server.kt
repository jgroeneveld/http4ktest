package http4ktest.adapter.api

import http4ktest.domain.SomeService
import org.http4k.core.Method
import org.http4k.core.then
import org.http4k.filter.DebuggingFilters
import org.http4k.filter.ServerFilters.CatchLensFailure
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.server.Http4kServer
import org.http4k.server.Jetty
import org.http4k.server.asServer

fun server(port: Int, someService: SomeService): Http4kServer {
    val appRoutes = routes(
        "/" bind Method.GET to rootHandler(someService),
        "/data" bind Method.POST to dataHandler
    )

    return DebuggingFilters
        .PrintRequestAndResponse()
        .then(CatchLensFailure)
        .then(appRoutes)
        .asServer(Jetty(port))
}
