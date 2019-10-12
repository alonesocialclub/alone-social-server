package social.alone.server.user.domain

import org.jetbrains.annotations.NotNull
import social.alone.server.image.Image
import javax.persistence.Embeddable
import javax.persistence.OneToOne
import javax.validation.constraints.Size


@Embeddable
data class Profile(
        @NotNull
        @Size(max = 20) var name: String = "",
        @OneToOne
        var image: Image? = null
) {
    fun updateName(name: String) {
        this.name = name
    }

    fun updateImage(image:Image) {
        this.image = image
    }
}