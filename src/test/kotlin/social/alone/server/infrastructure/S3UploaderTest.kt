package social.alone.server.infrastructure

import org.assertj.core.api.Assertions.assertThat
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import java.io.IOException
import java.net.URL

@RunWith(SpringRunner::class)
@SpringBootTest
class S3UploaderTest {

    @Autowired
    lateinit var s3Uploader: S3Uploader

    @Test
    @Throws(IOException::class)
    @Ignore
    fun upload_url() {
        val fileUrl = URL("https://alone.social/img/logo.png")
        val result = s3Uploader.upload("upload_url.jpeg", fileUrl)
        assertThat(result).isNotEmpty()
    }

}