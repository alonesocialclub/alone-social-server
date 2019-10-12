package social.alone.server.pickture

import org.springframework.data.jpa.repository.JpaRepository

interface PictureRepository: JpaRepository<Picture, String>