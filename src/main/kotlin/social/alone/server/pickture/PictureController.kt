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
    fun imageCreate(@RequestPart(value = "file") file: MultipartFile): ResponseEntity<*> {
        val pickture = imageUploader.upload(file)
        return ResponseEntity.ok(pickture)
    }

    @GetMapping("/pictures/{id}")
    fun imageRead(@PathVariable("id") picture: Picture): String {
        return "redirect:" + picture.url
    }
}