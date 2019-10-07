package social.alone.server.post.domain

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import social.alone.server.user.domain.User
import social.alone.server.image.Image
import java.time.LocalDateTime
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@Table(name = "posts")
class Post {
    @Id
    @GeneratedValue
    var id: Long? = null

    @CreationTimestamp
    val createdAt: LocalDateTime? = null

    @UpdateTimestamp
    var updatedAt: LocalDateTime? = null
        protected set

    @NotNull
    var text: String? = null

    @ManyToOne
    var image: Image? = null

    @ManyToOne
    var author: User? = null

    constructor(author: User, text: String?, image: Image?) {
        this.author = author
        this.image = image
        this.text = text
    }
}
