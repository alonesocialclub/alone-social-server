package social.alone.server

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import java.time.LocalDateTime
import java.util.TimeZone
import javax.annotation.PostConstruct


@SpringBootApplication

open class Application {
    companion object {

        @PostConstruct
        fun started() {
            TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"))
        }


        @JvmStatic
        fun main(args: Array<String>) {
            println("+=============")
            println(LocalDateTime.now())
            println("+=============")
            SpringApplication.run(Application::class.java, *args)
        }
    }
}
