package com.example.cocktail_proxy.controller

import jakarta.servlet.http.HttpServletResponse
import org.springframework.boot.web.servlet.error.ErrorController
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Controller for the endpoint "/error"
 *
 * A basic error controller to replace the default Tomcat WhitePage Error.
 * Handles primarily errors arising internally or locally.
*/
@RestController
@RequestMapping("/error")
class CustomErrorController : ErrorController {

    @GetMapping
    fun getErrorMessage(response: HttpServletResponse): String {
        return "A problem occurred. Status code ${response.status}"
    }
}