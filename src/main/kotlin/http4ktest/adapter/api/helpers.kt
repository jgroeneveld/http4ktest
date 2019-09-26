package http4ktest.adapter.api

import com.beust.klaxon.Klaxon
import org.http4k.core.Body
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.format.Jackson.auto

// Use klaxon for generating json because it seems more obvious than lenses
fun Response.jsonBody(value: Any?): Response {
    return this
        .header("Content-Type", "application/json")
        .body(Klaxon().toJsonString(value))
}

// Use lense to maintain compatibilty to framework
inline fun <reified T : Any> Request.parseBody(): T {
    return Body.auto<T>().toLens()(this)
}
