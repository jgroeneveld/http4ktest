package http4ktest

import org.http4k.core.Body
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.with
import org.http4k.format.Jackson.auto

val rootHandler = { request: Request ->
    val result = RootResponsePayload("root!", "blub")

    Response(Status.OK).with(rootResponseLens of result)
}

data class RootResponsePayload(
    val blub: String,
    val blab: String
)

val rootResponseLens = Body.auto<RootResponsePayload>().toLens()
