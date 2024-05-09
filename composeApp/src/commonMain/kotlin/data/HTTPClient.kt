package data;

import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

val httpClient = HttpClient {
    install(ContentNegotiation) {
        json(Json {
            ignoreUnknownKeys = true
            isLenient = true
            coerceInputValues = true
            prettyPrint = true
            explicitNulls = false 
        })
    }
    install(Logging) {
        logger = Logger.DEFAULT
        level = LogLevel.BODY
    }
}

val apiService = ApiService(httpClient)

