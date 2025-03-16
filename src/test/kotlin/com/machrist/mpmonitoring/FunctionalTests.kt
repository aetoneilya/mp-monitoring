package com.machrist.mpmonitoring

import liquibase.Liquibase
import liquibase.database.core.PostgresDatabase
import liquibase.database.jvm.JdbcConnection
import liquibase.resource.DirectoryResourceAccessor
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.clickhouse.ClickHouseContainer
import org.testcontainers.containers.JdbcDatabaseContainer
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.containers.wait.strategy.Wait.forLogMessage
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import java.io.File
import java.sql.DriverManager

@SpringBootTest
@Testcontainers
abstract class FunctionalTests {
    companion object {
        @Container
        val postgresContainer: PostgreSQLContainer<*> =
            PostgreSQLContainer("postgres:latest")
                .withDatabaseName("test-postgres")
                .withUsername("postgres")
                .withPassword("postgres")
                .also {
                    it.start()
                }.waitingFor(forLogMessage(".*Ready to accept connections.*\\n", 1))
                .also {
                    runMigrations(it)
                }

        @Container
        val clickhouseContainer: ClickHouseContainer =
            ClickHouseContainer("clickhouse/clickhouse-server:latest")
                .withUsername("clickhouse")
                .withPassword("clickhouse")
                .withDatabaseName("monitoring-test")
                .also { it.start() }

        private fun runMigrations(c: JdbcDatabaseContainer<*>) {
            val changelogPath = File("migrations")

            DriverManager.getConnection(c.jdbcUrl, c.username, c.password).use { conn ->
                val changelogDir = DirectoryResourceAccessor(changelogPath)
                val db = PostgresDatabase()
                db.connection = JdbcConnection(conn)

                val liquibase = Liquibase("master.yaml", changelogDir, db)
                liquibase.update()
            }
        }

        @DynamicPropertySource
        fun jdbcProperties(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url", postgresContainer::getJdbcUrl)
            registry.add("spring.datasource.username", postgresContainer::getUsername)
            registry.add("spring.datasource.password", postgresContainer::getPassword)

            registry.add("clickhouse.host", clickhouseContainer::getHost)
            registry.add("clickhouse.port", clickhouseContainer.getExposedPorts()::firstOrNull)
            registry.add("clickhouse.database", clickhouseContainer::getDatabaseName)
            registry.add("clickhouse.username", clickhouseContainer::getUsername)
            registry.add("clickhouse.password", clickhouseContainer::getPassword)
        }
    }
}


