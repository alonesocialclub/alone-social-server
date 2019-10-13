package social.alone.server.picture

import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import social.alone.server.infrastructure.S3Uploader


@Service
class ImageUploader(private val s3Uploader: S3Uploader, private val pictureRepository: PictureRepository) {

    fun upload(file: MultipartFile): Picture {
        val url = s3Uploader.upload(file)
        return pictureRepository.save(Picture(url))
    }
}