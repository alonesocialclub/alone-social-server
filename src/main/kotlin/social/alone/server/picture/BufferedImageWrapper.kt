package social.alone.server.picture

import java.awt.image.BufferedImage
import java.io.InputStream
import javax.imageio.ImageIO


class BufferedImageWrapper {
    val bufferedImage: BufferedImage
    val imageType: String
    constructor(bufferedImage: BufferedImage?, imageType: String?) {
        this.bufferedImage = bufferedImage!!
        this.imageType = imageType!!
    }

    companion object {
        fun getImageAndTypeFromInputStream(inputStream: InputStream): BufferedImageWrapper {
            var format: String? = null
            var bufferedImage: BufferedImage? = null
            ImageIO.createImageInputStream(`inputStream`).use { iis ->
                val readers = ImageIO.getImageReaders(iis)
                if (readers.hasNext()) {
                    val reader = readers.next()
                    format = reader.formatName
                    reader.input = iis
                    bufferedImage = reader.read(0)
                }
            }
            return BufferedImageWrapper(bufferedImage, format)
        }
    }
}