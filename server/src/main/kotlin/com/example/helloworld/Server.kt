package com.example.helloworld

import io.ktor.application.call
import io.ktor.application.install
import io.ktor.application.Application
import io.ktor.client.HttpClient
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.get
import io.ktor.features.CallLogging
import io.ktor.features.ContentNegotiation
import io.ktor.features.DefaultHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.serialization.json
import io.ktor.server.engine.ApplicationEngineEnvironment
import io.ktor.server.engine.applicationEngineEnvironment
import io.ktor.server.cio.CIO as ServerCIO
import io.ktor.client.engine.cio.CIO as ClientCIO
import io.ktor.server.engine.embeddedServer
import io.ktor.util.KtorExperimentalAPI
import kotlinx.serialization.UnstableDefault
import kotlinx.serialization.json.Json

@UnstableDefault
@KtorExperimentalAPI
fun Application.module() {
    val client = HttpClient(ClientCIO) {
        install(JsonFeature) {
            serializer = KotlinxSerializer(Json {
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
    }

    install(DefaultHeaders)
    install(CallLogging)
    install(ContentNegotiation) {
        json()
    }
    install(Routing) {
        get("/") {
            val releases = client.get<List<Release>>("https://api.github.com/repos/jetbrains/kotlin/tags")

            releases.filterNot{it.name.contains("-")}.firstOrNull()?.let {
                call.respond(it)
            } ?: call.respond(HttpStatusCode.NotFound)
        }
    }
}

@UnstableDefault
@KtorExperimentalAPI
fun main(args: Array<String>) {
    val port = System.getenv("PORT")?.toInt() ?: 8080

    val watchPaths = if (args.contains("prod")) {
        emptyList()
    } else {
        listOf("build")
    }

    embeddedServer(ServerCIO, port, watchPaths = watchPaths, module = Application::module).start(true)
}
