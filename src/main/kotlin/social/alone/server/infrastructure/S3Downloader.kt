package social.alone.server.infrastructure

import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.S3Object
import com.amazonaws.services.s3.model.CannedAccessControlList
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectRequest
import com.amazonaws.util.IOUtils
import lombok.extern.slf4j.Slf4j
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.io.BufferedInputStream
import java.io.ByteArrayInputStream
import java.io.File
import java.io.IOException
import java.net.URL
import java.util.*


@Slf4j
@Component
class S3Downloader(private val amazonS3Client: AmazonS3Client) {
    companion object {
        const val bucket = "alone-social-static-image"
    }

    fun download(keyName: String): S3Object {
        return amazonS3Client.getObject(bucket, keyName)
    }
}
