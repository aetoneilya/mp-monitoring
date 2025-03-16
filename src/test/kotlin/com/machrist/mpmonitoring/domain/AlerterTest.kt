package com.machrist.mpmonitoring.domain

import com.machrist.mpmonitoring.client.TelegramBotClient
import com.machrist.mpmonitoring.metric.model.TimeSeries
import com.machrist.mpmonitoring.metric.model.TimeSeriesDataPoint
import com.machrist.mpmonitoring.model.Alert
import com.machrist.mpmonitoring.model.ChannelType.TELEGRAM
import com.machrist.mpmonitoring.model.NotificationChannel
import com.machrist.mpmonitoring.repository.AlertRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.time.Duration
import java.time.Instant
import java.time.OffsetDateTime

class AlerterTest {
    private lateinit var alertRepository: AlertRepository
    private lateinit var metricService: MetricService
    private lateinit var telegramBotClient: TelegramBotClient

    private lateinit var alerter: Alerter

    @BeforeEach
    fun setUp() {
        alertRepository = mock()
        metricService = mock()
        telegramBotClient = mock()
        alerter = Alerter(alertRepository, metricService, telegramBotClient)
    }

    @Test
    fun `checkAlerts should send alert when anomaly detected`() {
        val alert = buildTestAlert()
        val metricValues = listOf(TimeSeries(listOf(TimeSeriesDataPoint(dateTime = OffsetDateTime.now(), value = 1.0))))
        whenever(alertRepository.findAlertToCheck()).thenReturn(listOf(alert))
        whenever(metricService.getMetricByPayload(any(), anyOrNull(), any())).thenReturn(metricValues)

        // Act
        alerter.checkAlerts()

        // Assert
        verify(telegramBotClient).sendAlertToBot(any(), any(), any(), any())
    }

    fun buildTestAlert() : Alert = Alert(
        name = "Alert",
        project = null,
        rule = mapOf(),
        lastCalculation =
        Instant.now()
            .minusSeconds(60 * 5),
        calculationInterval = Duration.ofMinutes(5),
        notificationChannels =
        mutableSetOf(
            NotificationChannel(
                type = TELEGRAM,
                address = mutableMapOf("login" to "test",),
            ),
        ),
    )
}
