import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.CORS
import io.ktor.features.Compression
import io.ktor.features.ContentNegotiation
import io.ktor.features.gzip
import io.ktor.html.respondHtml
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.http.content.resources
import io.ktor.http.content.static
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.route
import io.ktor.routing.routing
import io.ktor.serialization.json
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import kotlinx.html.HTML
import kotlinx.html.body
import kotlinx.html.div
import kotlinx.html.h1
import kotlinx.html.head
import kotlinx.html.id
import kotlinx.html.script
import kotlinx.html.title
import kotlinx.serialization.json.Json
import mu.KotlinLogging

private val log = KotlinLogging.logger {}

fun HTML.index() {
    head {
        title("HTML Grader")

    }
    body {
        h1 {
            +"HTML Grader"
        }
        div {
            id = "root"
        }
        script(src = "/static/output.js") {}
    }
}

fun main() {
    val gradeService = GradeService.create()

    embeddedServer(Netty, port = 8080, host = "127.0.0.1") {
        install(ContentNegotiation) {
            json(json = Json { useArrayPolymorphism = false })
        }
        install(CORS) {
            method(HttpMethod.Post)
            anyHost()
        }
        install(Compression) {
            gzip()
        }

        routing {
            get("/") {
                call.respondHtml(OK, HTML::index)
            }
            static("/static") {
                resources()
            }
            route(gradeResource) {
                post {
                    val uri = call.receive<Submission>().uri
                    val grade = gradeService.grade(uri)
                    log.info { "Calculated grade for $uri -> $grade" }
                    call.respond(OK, grade)
                }
            }
        }
    }.start(wait = true)
}