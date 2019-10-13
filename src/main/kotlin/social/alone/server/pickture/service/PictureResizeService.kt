package social.alone.server.pickture.service

import java.awt.image.BufferedImage
import org.imgscalr.Scalr
import org.springframework.stereotype.Service

@Service
class PictureResizeService {
    fun resize(img: BufferedImage, size: String): BufferedImage {
        var width: Int
        when (size) {
            "hd" -> width = 640
            "large" -> width = 320
            "medium" -> width = 200
            "thumbnail" -> width = 128
            "small" -> width = 64
            "tiny" -> width = 40
            else -> width = 0
        }
        var resizedImg = img
        if (width > 0) {
            resizedImg = Scalr.resize(img, Scalr.Method.AUTOMATIC, Scalr.Mode.FIT_TO_WIDTH, width, null)
        }
        return resizedImg
    }
}
