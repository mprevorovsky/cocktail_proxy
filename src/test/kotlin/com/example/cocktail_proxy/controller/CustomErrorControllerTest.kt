package com.example.cocktail_proxy.controller

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.web.client.RestTemplate


@SpringBootTest
class CustomErrorControllerTest {

    @Autowired
    lateinit var restTemplate: RestTemplate

    // Flaky test!!!
    @Test
    fun `should call trigger error message from CustomErrorController`() {
        // given
        val nonExistingLocalUri = "http://localhost:8080/foo"

        // when

        /*
        The following step produces one of two different outcomes

        HttpClientErrorException$NotFound: 404 : "A problem occurred. Status code 404".
          (This would indicate that the CustomErrorController is being used.)
          or
        ResourceAccessException: I/O error on GET request for "http://localhost:8080/foo": Spojení odmítnuto

        In either case, I do not understand why an error is triggered instead of just showing the response
        from the CustomErrorController.
        I suspect that the "/error" endpoint behaviour is not fully functional under @SpringBootTest.
        Or maybe error handling needs to be set up for the RestTemplate?
        */

        val result = restTemplate.getForEntity(nonExistingLocalUri, String::class.java)

        // then
        assertThat(result.body).contains("A problem occurred. Status code 404")
    }
}