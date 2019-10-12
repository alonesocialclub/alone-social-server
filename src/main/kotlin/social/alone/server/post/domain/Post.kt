package social.alone.server.post.domain

import org.hibernate.annotations.GenericGenerator
import social.alone.server.pickture.Picture
import social.alone.server.user.domain.User
import java.time.LocalDateTime
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@Table(name = "post")
class Post(@ManyToOne var author: User, @NotNull var text: String, @OneToOne var picture: Picture) {

    @Id
    @Column(unique = true, columnDefinition = "VARCHAR(64)")
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    val id: String? = null

    val createdAt: LocalDateTime = LocalDateTime.now()

    var updatedAt: LocalDateTime = LocalDateTime.now()

}
