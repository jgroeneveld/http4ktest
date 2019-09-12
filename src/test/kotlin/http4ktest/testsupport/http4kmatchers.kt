package http4ktest.testsupport

import assertk.Assert
import assertk.assertions.isEqualTo
import assertk.assertions.prop
import org.http4k.core.Body
import org.http4k.core.Response
import org.http4k.core.Status

fun Assert<Response>.isOk(): Assert<Response> = this.hasStatus(Status.OK)

fun Assert<Response>.hasStatus(status: Status): Assert<Response> {
    prop("status", Response::status).isEqualTo(status)
    return this
}

fun Assert<Response>.hasBody(body: String): Assert<Response> {
    prop("body", Response::body).isEqualTo(Body(body))
    return this
}