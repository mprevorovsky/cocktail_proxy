package com.example.cocktail_proxy.datasource

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.web.client.RestTemplate


class ProxyDataSourceTest {

    private val restTemplate = RestTemplate()
    private val dataSource = ProxyDataSource(restTemplate)

    @Test
    fun `should properly compose requested URI from base, path and query`() {

        //given
        val baseUrl = "http://example.com/"
        val path = "filter.php"
        val queryString = "i=2"

        // when
        val uri1 = dataSource.buildRequestUri(baseUrl, path, null)
        val uri2 = dataSource.buildRequestUri(baseUrl, path, queryString)

        // then
        assertThat(uri1).isEqualTo(baseUrl + path)
        assertThat(uri2).isEqualTo(baseUrl + path + "?" + queryString)
    }
}

/*
It would be nice to also test if the actual http request used the correct
- method (GET)
- URI
etc.
But I do not know how to do it.
(I tried using a HttpServletRequest object injected by Spring.)
*/