package com.machrist.mpmonitoring.client

import com.machrist.mpmonitoring.openapi.dto.ApiErrorResponse
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.HttpStatusCode
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.BodyInserters.fromValue
import org.springframework.web.reactive.function.client.ClientResponse
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import java.time.Instant

@Service
class TelegramBotClient(
    @Qualifier("tgBotClient") private val webClient: WebClient,
) {
    fun sendAlertToBot(
        alertName: String,
        from: Instant,
        to: Instant,
        userLogin: String,
    ) = sendAlertToBot(
        TelegramAlertRequest(
            alertName,
            from,
            to,
            userLogin,
        ),
    )

    fun sendAlertToBot(alertRequest: TelegramAlertRequest) {
        webClient.post().uri("/sendAlert")
            .body(fromValue(alertRequest))
            .retrieve()
            .onStatus({ obj: HttpStatusCode -> obj.isError }) { response: ClientResponse ->
                response.bodyToMono(
                    ApiErrorResponse::class.java,
                )
                    .flatMap { error: ApiErrorResponse ->
                        Mono.error(
                            TgBotClientException(error.exceptionMessage ?: "Unknown error"),
                        )
                    }
            }
            .bodyToMono(Void::class.java)
            .block()
    }
}
