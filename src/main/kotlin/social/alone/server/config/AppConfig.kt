package social.alone.server.config


import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@Configuration
class AppConfig {

    @Autowired
    var appProperties: AppProperties? = null

    @Bean
    fun modelMapper(): ModelMapper {
        return ModelMapper()
    }

    @Bean
    internal fun restTemplate(): RestTemplate {
        return RestTemplate()
    }

}
