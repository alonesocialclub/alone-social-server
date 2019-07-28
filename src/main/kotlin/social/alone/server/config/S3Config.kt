package social.alone.server.config

import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component

@Component
object S3Config {

    @Bean
    fun amazonS3Client(): AmazonS3Client {
        return AmazonS3ClientBuilder.standard()
                .withRegion("ap-northeast-2")
                .build() as AmazonS3Client
    }
}
