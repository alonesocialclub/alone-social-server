package social.alone.server.push;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
class FcmToken {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String value;

    FcmToken(String value){
        this.value = value;
    }

}
