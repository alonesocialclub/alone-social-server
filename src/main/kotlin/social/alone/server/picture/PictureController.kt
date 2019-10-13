package social.alone.server.picture

import java.io.ByteArrayOutputStream
import javax.imageio.ImageIO

import org.springframework.http.ResponseEntity
import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.multipart.MultipartFile
import social.alone.server.picture.service.PictureResizeService
import java.io.ByteArrayInputStream
import java.lang.NumberFormatException
import java.net.URLConnection
import java.net.URLConnection.guessContentTypeFromStream



@Controller
class PictureController(private val imageUploader: ImageUploader, private val imageDownloader: ImageDownloader, private val pictureResizeService: PictureResizeService) {

    @PostMapping("/pictures")
    fun imageCreate(@RequestPart(value = "file") file: MultipartFile): ResponseEntity<Picture> {
        val picture = imageUploader.upload(file)
        return ResponseEntity.ok(picture)
    }

    @GetMapping("/pictures/{id}")
    fun readPicture(@PathVariable("id") picture: Picture): ResponseEntity<Picture> {
        return ResponseEntity.ok(picture)
    }

    @GetMapping("/pictures/{id}/image/{size}")
    fun pictureReadByImageSize(
        @PathVariable("id") picture: Picture,
        @PathVariable("size") size: String
    ): ResponseEntity<*> {
        // TODO image read wtih size
        val pictureUrlSplit = picture.url.split("/")
        val pictureKeyName = pictureUrlSplit[pictureUrlSplit.size-1]
        val splitSize = size.split("x")

        var width = 0
        var height = 0
        try {
            width = splitSize[0].toInt()
        } catch (e: NumberFormatException){
        }
        try {
            height = splitSize[1].toInt()
        } catch (e: NumberFormatException) {
        }

        val imgWrapper = imageDownloader.download(pictureKeyName)
        var img = pictureResizeService.resize(imgWrapper.bufferedImage, width, height)

        val bao = ByteArrayOutputStream()
        ImageIO.write(img, imgWrapper.imageType, bao)
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(bao.toByteArray())
    }
}
