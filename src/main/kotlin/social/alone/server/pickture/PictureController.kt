package social.alone.server.pickture

import java.io.ByteArrayOutputStream
import java.io.ByteArrayInputStream
import javax.imageio.ImageIO

import org.springframework.http.ResponseEntity
import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.multipart.MultipartFile
import social.alone.server.pickture.service.PictureResizeService

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
        val pictureKeyNameSplit = pictureKeyName.split(".")
        val pictureExtension = pictureKeyNameSplit[pictureKeyNameSplit.size-1]
        var img = imageDownloader.download(pictureKeyName)
        img = pictureResizeService.resize(img, size)
        val bao = ByteArrayOutputStream();
        ImageIO.write(img, pictureExtension, bao)
        return ResponseEntity.ok().contentType(MediaType.ALL).body(bao.toByteArray())
    }
}
