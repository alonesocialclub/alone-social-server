package social.alone.server

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


@RestController
class HelloKotlin {

    @GetMapping("/api/kt")
    fun blog(): String {
        return "kt"
    }

}