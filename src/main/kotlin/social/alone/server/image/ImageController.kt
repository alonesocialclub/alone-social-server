package social.alone.server.image

import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.multipart.MultipartFile


@Controller
class ImageController(private val imageUploader: ImageUploader) {

    @PostMapping("/images")
    fun imageCreate(@RequestPart(value = "file") file: MultipartFile): ResponseEntity<*> {
        val image = imageUploader.upload(file)
        return ResponseEntity.ok(image)
    }

    @GetMapping("/images/{id}")
    fun imageRead(@PathVariable("id") image: Image): String {
        return "redirect:" + image.url
    }
}