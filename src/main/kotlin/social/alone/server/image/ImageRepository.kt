package social.alone.server.image

import org.springframework.data.jpa.repository.JpaRepository

interface ImageRepository: JpaRepository<Image, String>