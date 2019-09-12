package http4ktest

import org.http4k.core.*
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