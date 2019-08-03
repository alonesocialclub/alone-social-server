package social.alone.server.interest

import com.fasterxml.jackson.annotation.JsonIgnore
import social.alone.server.user.domain.User
import lombok.AccessLevel
import lombok.EqualsAndHashCode
import lombok.Getter
import lombok.NoArgsConstructor

import javax.persistence.*
import java.util.HashSet

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(indexes = [Index(name = "idx_value", columnList = "value", unique = true)])
@EqualsAndHashCode(of = ["value"])
class Interest {

    @Id
    @GeneratedValue
    val id: Long? = null

    @Column(nullable = false, unique = true)
    val value: String

    @ManyToMany(mappedBy = "interests")
    @JsonIgnore
    val users: Set<User>

    constructor(value: String) {
        this.value = value
        this.users = HashSet()
    }

}
