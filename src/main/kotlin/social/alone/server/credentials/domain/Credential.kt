package social.alone.server.credentials.domain

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import social.alone.server.user.domain.User
import java.time.LocalDateTime
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@Table(name = "credentials", uniqueConstraints = [UniqueConstraint(columnNames = ["provider", "identifier"])])
data class Credential(@ManyToOne var user: User) {

    @Id
    @GeneratedValue
    @Column
    val id: Long? = null

    @NotNull
    var provider: String? = null

    @NotNull
    var identifier: String? = null

    @CreationTimestamp
    val createdAt: LocalDateTime? = null

    @UpdateTimestamp
    var updatedAt: LocalDateTime? = null
        protected set

    constructor(provider: String, identifier: String, user: User) : this(user) {
        this.provider = provider
        this.identifier = identifier
    }
}
