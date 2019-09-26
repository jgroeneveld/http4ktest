package http4ktest.adapter.api

import com.beust.klaxon.Json
import http4ktest.domain.SomeService
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status

fun RootHandler(service: SomeService) = { _: Request ->
    val result = RootResponsePayload(
        "root!",
        service.doStuff()
    )

    Response(Status.OK).jsonBody(result)
}

data class RootResponsePayload(
    @Json("foo_bar")
    val fooBar: String,

    @Json("lorem_ipsum")
    val loremIpsum: String
)

