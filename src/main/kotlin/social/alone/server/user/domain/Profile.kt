package social.alone.server.user.domain

import org.jetbrains.annotations.NotNull
import social.alone.server.pickture.Picture
import javax.persistence.Embeddable
import javax.persistence.OneToOne
import javax.validation.constraints.Size


@Embeddable
data class Profile(
        @NotNull
        @Size(max = 20) var name: String = "",
        @OneToOne
        var picture: Picture? = null
) {
    fun updateName(name: String) {
        this.name = name
    }

    fun updateImage(picture:Picture) {
        this.picture = picture
    }
}