package com.example.cocktail_proxy

import com.example.cocktail_proxy.datasource.ProxyDataSource
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
Integration tests are badly needed. But not sure how to go about them (mocking? real requests over internet?).
Things to check:
- the request type is GET
- the response status code is 200
*/
