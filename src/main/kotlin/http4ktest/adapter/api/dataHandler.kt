package http4ktest.adapter.api

import http4ktest.domain.SomeService
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status

val dataHandler = { request: Request ->
    val data = request.parseBody<DataRequestPayload>()
    val service = SomeService()

    val result = DataResponsePayload(
        "${service.doStuff()} '${data.a}'",
        "blub"
    )

    Response(Status.OK).jsonBody(result)
}

data class DataRequestPayload(
    val a: String,
    val b: String
)

data class DataResponsePayload(
    val blub: String,
    val blab: String
)

