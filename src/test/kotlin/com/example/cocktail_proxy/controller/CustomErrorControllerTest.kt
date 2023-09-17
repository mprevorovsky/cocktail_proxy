package com.example.cocktail_proxy.controller

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CustomErrorControllerTest {

    @Autowired
    lateinit var restTemplate: TestRestTemplate

    @Test
    fun `should call trigger error message from CustomErrorController`() {
        // given
        val nonExistingLocalUri = "/foo"

        // when
        val result = restTemplate.getForEntity(nonExistingLocalUri, String::class.java)

        // then
        assertThat(result.body).contains("A problem occurred. Status code 404")
    }
}