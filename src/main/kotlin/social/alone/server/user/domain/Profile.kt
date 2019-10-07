package social.alone.server.user.domain

import org.jetbrains.annotations.NotNull
import javax.persistence.Embeddable
import javax.validation.constraints.Size


@Embeddable
data class Profile(

        @NotNull
        @Size(max = 20) var name: String
) {
    fun updateName(name: String) {
        this.name = name
    }
}