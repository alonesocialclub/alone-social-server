package social.alone.server.infrastructure

import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

import java.io.IOException

@RunWith(SpringRunner::class)
@SpringBootTest
class S3UploaderTest {

    @Autowired
    internal var s3Uploader: S3Uploader? = null

    @Test
    @Ignore
    @Throws(IOException::class)
    fun test() {
        val fileUrl = "https://platform-lookaside.fbsbx.com/platform/profilepic/?asid=2256853227925892&height=250&width=250&ext=1566891315&hash=AeSIdtyadPIWL4_z"
        val result = s3Uploader!!.upload("test.jpeg", fileUrl)
        println(result)
    }

}