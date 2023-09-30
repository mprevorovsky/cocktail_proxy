package com.example.cocktail_proxy.config

import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

/**
 * Setup of beans required by the application.
 */
@Configuration
class CocktailDbProxyApplicationConfiguration {

    @Bean
    fun restTemplate(): RestTemplate = RestTemplateBuilder().build()
}
