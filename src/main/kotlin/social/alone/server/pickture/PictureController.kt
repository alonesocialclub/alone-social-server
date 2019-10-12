package social.alone.server.pickture

import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.multipart.MultipartFile


@Controller
class PictureController(private val imageUploader: ImageUploader) {

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
            @PathVariable("size") size :String
    ): String {
        // TODO image read wtih size
        println(size)
        return "redirect:" + picture.url
    }
}