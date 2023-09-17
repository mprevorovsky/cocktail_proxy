package com.example.cocktail_proxy.controller

import jakarta.servlet.http.HttpServletResponse
import org.springframework.boot.web.servlet.error.ErrorController
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


/*
Basic error controller to replace the default WhitePage Error.
*/


@RestController
@RequestMapping("/error")
@ControllerAdvice
class CustomErrorController: ErrorController {

    @GetMapping
    fun getErrorMessage(response: HttpServletResponse): String {
        return "A problem occurred. Status code ${response.status}"
    }
}