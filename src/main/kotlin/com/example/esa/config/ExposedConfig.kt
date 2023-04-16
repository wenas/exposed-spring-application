package com.example.esa.config

import org.jetbrains.exposed.spring.autoconfigure.ExposedAutoConfiguration
import org.jetbrains.exposed.sql.DatabaseConfig
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource

@Configuration
class ExposedConfig {
    @Bean
    fun exposedAutoConfiguration(applicationContext: ApplicationContext) =
        ExposedAutoConfiguration(applicationContext)

    @Bean
    fun springTransactionManager(exposedAutoConfiguration: ExposedAutoConfiguration, datasource: DataSource, databaseConfig: DatabaseConfig) =
        exposedAutoConfiguration.springTransactionManager(datasource, databaseConfig)

    @Bean
    fun databaseConfig(exposedAutoConfiguration: ExposedAutoConfiguration, ) =
        exposedAutoConfiguration.databaseConfig()
}