package social.alone.server.ping

import org.hibernate.annotations.GenericGenerator
import social.alone.server.post.domain.Post
import social.alone.server.user.domain.User
import java.time.LocalDateTime
import javax.persistence.*


@Entity
@Table
class Ping(
        @ManyToOne var sender: User,
        @ManyToOne var receiver: User,
        @ManyToOne var post: Post
) {

    @Id
    @Column(unique = true, columnDefinition = "VARCHAR(64)")
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    var id: String? = null

    val createdAt: LocalDateTime = LocalDateTime.now()

    var updatedAt: LocalDateTime = LocalDateTime.now()
}