package http4ktest.adapter.api

import com.beust.klaxon.Klaxon
import org.http4k.core.Response

fun Response.jsonBody(value: Any?): Response {
    return this
        .header("Content-Type", "application/json")
        .body(Klaxon().toJsonString(value))
}
