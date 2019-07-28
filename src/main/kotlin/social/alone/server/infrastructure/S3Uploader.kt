package social.alone.server.infrastructure

import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.CannedAccessControlList
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectRequest
import com.amazonaws.util.IOUtils
import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.springframework.stereotype.Component

import java.io.BufferedInputStream
import java.io.ByteArrayInputStream
import java.io.IOException
import java.io.InputStream
import java.net.URL

@Slf4j
@RequiredArgsConstructor
@Component
class S3Uploader {

    private val amazonS3Client: AmazonS3Client? = null

    fun upload(path: String, fileUrl: String): String? {
        try {

            val url = URL(fileUrl)
            val inputStream = BufferedInputStream(url.openStream())
            val contents = IOUtils.toByteArray(inputStream)
            val stream = ByteArrayInputStream(contents)
            val meta = ObjectMetadata()
            meta.contentLength = contents.size.toLong()
            meta.contentType = "image/jpeg"

            val bucket = "alone-social-static-image"


            amazonS3Client!!
                    .putObject(
                            PutObjectRequest(bucket, path, stream, meta).withCannedAcl(CannedAccessControlList.PublicRead)
                    )
            return amazonS3Client.getUrl(bucket, path).toString()
        } catch (e: IOException) {
            return null
        }

    }
}
