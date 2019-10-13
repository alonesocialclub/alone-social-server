package social.alone.server.picture

import org.springframework.stereotype.Service
import social.alone.server.infrastructure.S3Downloader
import java.awt.image.BufferedImage
import javax.imageio.ImageIO


@Service
class ImageDownloader(private val s3Downloader: S3Downloader) {
    fun download(imageName: String): BufferedImage{
        val imageObj = s3Downloader.download(imageName)
        val bufferedImage = ImageIO.read(imageObj.objectContent)
        return bufferedImage
    }
}
