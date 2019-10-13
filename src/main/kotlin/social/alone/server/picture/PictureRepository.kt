package social.alone.server.picture

import org.springframework.data.jpa.repository.JpaRepository

interface PictureRepository: JpaRepository<Picture, String>