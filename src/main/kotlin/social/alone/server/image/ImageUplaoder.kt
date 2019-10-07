package social.alone.server.image

import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import social.alone.server.infrastructure.S3Uploader


@Service
class ImageUploader(private val s3Uploader: S3Uploader, private val imageRepository: ImageRepository) {

    fun upload(file: MultipartFile): Image {
        val url = s3Uploader.upload(file)
        return imageRepository.save(Image(url))
    }
}