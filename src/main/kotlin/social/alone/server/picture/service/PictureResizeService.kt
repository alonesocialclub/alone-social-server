package social.alone.server.picture.service

import java.awt.image.BufferedImage
import org.imgscalr.Scalr
import org.springframework.stereotype.Service

@Service
class PictureResizeService {
    fun resize(img: BufferedImage, width: Int, height: Int): BufferedImage {
        var resizedImg = img
        if (width > 0) {
            resizedImg = Scalr.resize(img, Scalr.Method.AUTOMATIC, Scalr.Mode.FIT_TO_WIDTH, width, null)
        }
        return resizedImg
    }
}
