package com.example.cocktail_proxy.controller

import jakarta.servlet.http.HttpServletResponse
import org.springframework.boot.web.servlet.error.ErrorController
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController


/*
Basic error controller to replace the default WhitePage Error.

Note:
When the target URL does not exist on the Cocktail DB server,
the server sends the 404 status code, together with an HTML document describing the error.
This then manifests as the 500 status code returned by the Proxy app.
*/


@RestController
@RequestMapping
class CustomErrorController: ErrorController {

    @ResponseBody
    @GetMapping("/error")
    fun getErrorMessage(response: HttpServletResponse): String {
        return "A problem occurred. Status code ${response.status}"
    }
}