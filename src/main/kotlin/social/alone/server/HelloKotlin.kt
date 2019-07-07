package social.alone.server

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


@RestController
class HelloKotlin {

    @GetMapping("/api/kt")
    fun kt(): String {
        return "kt"
    }


    @GetMapping("/api/exception")
    fun exception(): String {
        throw Exception("intended exception raised!")
    }

}