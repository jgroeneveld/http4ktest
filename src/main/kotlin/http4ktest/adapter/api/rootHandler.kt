package http4ktest.adapter.api

import http4ktest.domain.SomeService
import org.http4k.core.Body
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.with
import org.http4k.format.Jackson.auto

fun rootHandler(service: SomeService) = { _: Request ->
    val result = RootResponsePayload(
        "root!",
        service.doStuff()
    )

    Response(Status.OK).with(rootResponseLens of result)
}

data class RootResponsePayload(
    val blub: String,
    val blab: String
)

val rootResponseLens = Body.auto<RootResponsePayload>().toLens()
