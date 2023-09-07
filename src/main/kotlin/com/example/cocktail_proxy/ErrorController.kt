package com.example.cocktail_proxy

import jakarta.servlet.http.HttpServletResponse
import org.springframework.boot.web.servlet.error.ErrorController
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

// When the target URL does not exist on the Cocktail DB server,
// the server sends the 404 status code, together with an HTML document describing the error.
// However, the Proxy app expects to receive a JsonNode object from the server.
// When the app does not get such an object, this results in an error
// and the status code 500 is produced and displayed by the Proxy app.

@RestController
@RequestMapping
class CustomErrorController : ErrorController {

    @ResponseBody
    @GetMapping("/error")
    fun getErrorMessage(response: HttpServletResponse): String {
        return "A problem occurred. Status code ${response.status}"
    }
}