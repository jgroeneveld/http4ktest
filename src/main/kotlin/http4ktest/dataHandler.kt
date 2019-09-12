package http4ktest

import org.http4k.core.Body
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.with
import org.http4k.format.Jackson.auto

val dataHandler = { request: Request ->
    val data = DataRequestPayload.fromRequest(request)

    val result = DataResponsePayload("bla '${data.a}'", "blub")

    Response(Status.OK).with(dataResponseLens of result)
}

data class DataRequestPayload(
    val a: String,
    val b: String
) {
    companion object {
        fun fromRequest(request: Request): DataRequestPayload {
            return Body.auto<DataRequestPayload>().toLens()(request)
        }
    }
}

data class DataResponsePayload(
    val blub: String,
    val blab: String
)

val dataResponseLens = Body.auto<DataResponsePayload>().toLens()
