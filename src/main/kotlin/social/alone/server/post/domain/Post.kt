package social.alone.server.post.domain

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import social.alone.server.image.Image
import social.alone.server.user.domain.User
import java.time.LocalDateTime
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@Table(name = "post")
class Post(@ManyToOne var author: User, @NotNull var text: String, @OneToOne var image: Image) {
    @Id
    @GeneratedValue
    var id: Long? = null

    @CreationTimestamp
    val createdAt: LocalDateTime? = null

    @UpdateTimestamp
    var updatedAt: LocalDateTime? = null
        protected set

}
