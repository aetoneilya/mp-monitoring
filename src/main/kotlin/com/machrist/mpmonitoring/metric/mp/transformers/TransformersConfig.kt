package com.machrist.mpmonitoring.metric.mp.transformers

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class TransformersConfig {
    @Bean
    fun averageTransformers(): TimeSeriesTransformer = AverageTransformer()

    @Bean
    fun matrixProfileTransformer(): TimeSeriesTransformer = MatrixProfileTransformer()

    @Bean
    fun maxTransformers(): TimeSeriesTransformer = MaxTransformer()

    @Bean
    fun minTransformers(): TimeSeriesTransformer = MinTransformer()

    @Bean
    fun movingAverageTransformer(): TimeSeriesTransformer = MovingAverageTransformer()

    @Bean
    fun emaTransformer(): TimeSeriesTransformer = EmaTransformer()

    @Bean
    fun differencingTransformer(): TimeSeriesTransformer = DifferencingTransformer()

    @Bean
    fun fourierTransformTransformer(): TimeSeriesTransformer = FourierTransformTransformer()

    @Bean
    fun integratingTransformer(): TimeSeriesTransformer = IntegratingTransformer()

    @Bean
    fun standardDeviationTransformer(): TimeSeriesTransformer = StandardDeviationTransformer()

    @Bean
    fun zScoreNormalizationTransformer(): TimeSeriesTransformer = ZScoreNormalizationTransformer()
}
