package social.alone.server.infrastructure

import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.CannedAccessControlList
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectRequest
import com.amazonaws.util.IOUtils
import jdk.nashorn.internal.ir.annotations.Ignore
import lombok.extern.slf4j.Slf4j
import org.springframework.stereotype.Component
import java.io.BufferedInputStream
import java.io.ByteArrayInputStream
import java.io.IOException
import java.net.URL
import java.util.*

@Slf4j
@Component
@Ignore
class S3Uploader(private val amazonS3Client: AmazonS3Client) {
    companion object {
        const val bucket = "alone-social-static-image"
    }

    fun upload(path: String, imageUrl: URL): String? {
        try {
            val inputStream = BufferedInputStream(imageUrl.openStream())
            val contents = IOUtils.toByteArray(inputStream)
            val stream = ByteArrayInputStream(contents)
            val meta = ObjectMetadata()
            meta.contentType = "image/jpeg"

            amazonS3Client
                    .putObject(
                            PutObjectRequest(bucket, path, stream, meta).withCannedAcl(CannedAccessControlList.PublicRead)
                    )
            return amazonS3Client.getUrl(bucket, path).toString()
        } catch (e: IOException) {
            print(e.stackTrace)
            return null
        }

    }

    fun upload(path: String, base64encoded: String): String? {
        return try {
            val stream = ByteArrayInputStream(Base64.getDecoder().decode(base64encoded))
            val meta = ObjectMetadata()
            meta.contentType = "image/jpeg"

            amazonS3Client
                    .putObject(
                            PutObjectRequest(bucket, path, stream, meta).withCannedAcl(CannedAccessControlList.PublicRead)
                    )
            return amazonS3Client.getUrl(bucket, path).toString()
        } catch (e: IOException) {
            print(e.stackTrace)
            null
        }
    }
}
