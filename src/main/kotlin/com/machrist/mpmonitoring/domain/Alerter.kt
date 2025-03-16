package com.machrist.mpmonitoring.domain

import com.machrist.mpmonitoring.client.TelegramBotClient
import com.machrist.mpmonitoring.model.Alert
import com.machrist.mpmonitoring.model.ChannelType
import com.machrist.mpmonitoring.repository.AlertRepository
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import java.time.ZoneOffset

@Component
class Alerter(
    private val alertRepository: AlertRepository,
    private val metricService: MetricService,
    private val telegramBotClient: TelegramBotClient,
) {
    @Scheduled(cron = "0 */5 * * * *")
    @Transactional
    fun checkAlerts() {
        val alerts = alertRepository.findAlertToCheck()
        alerts.forEach(::checkAlert)
    }

    private fun checkAlert(alert: Alert) {
        var from = alert.lastCalculation
        var to = from + alert.calculationInterval

        while (to <= Instant.now()) {
            val ts = metricService.getMetricByPayload(alert.rule, from.atOffset(), to.atOffset())
            if (ts.any { it.last().value != 0.0 }) {
                sendAlertMessage(alert, from, to)
            }
            from = to
            to += alert.calculationInterval
        }
    }

    private fun sendAlertMessage(
        alert: Alert,
        from: Instant,
        to: Instant,
    ) {
        for (notificationChannel in alert.notificationChannels) {
            when (notificationChannel.type) {
                ChannelType.TELEGRAM ->
                    notificationChannel.address["login"]?.let {
                        telegramBotClient.sendAlertToBot(alert.name, from, to, it)
                    }

                ChannelType.PUSH, ChannelType.EMAIL -> TODO("Not yet implemented")
                null -> continue
            }
        }
    }

    private fun Instant.atOffset() = atOffset(ZoneOffset.UTC)
}
