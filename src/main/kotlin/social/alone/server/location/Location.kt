package social.alone.server.location

import lombok.AccessLevel
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.ToString

import javax.persistence.*


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = ["id", "name"])
class Location {

    @Id
    @GeneratedValue
    var id: Long? = null


    var address: String

    var name: String

    var longitude: Double? = null

    var latitude: Double? = null

    var placeUrl: String

    @Column(nullable = false, columnDefinition = "varchar(255) default 'https://alone.social/cafe/random/0.jpg'")
    var imageUrl = DEFAULT_IMAGE

    val imageUrlByName: String
        get() {
            val idx = (Math.random() * 3).toInt()
            val randomImage = "$IMAGE_HOST/cafe/random/$idx.jpg"

            if (this.name.contains("스타벅스")) {
                return "$IMAGE_HOST/cafe/starbucks.jpg"
            }

            if (this.name.contains("이디야")) {
                return "$IMAGE_HOST/cafe/ediya.jpg"
            }


            if (this.name.contains("할리스")) {
                return "$IMAGE_HOST/cafe/hollys.jpg"
            }

            return if (this.name.contains("폴바셋")) {
                "$IMAGE_HOST/cafe/paulbassett.jpg"
            } else randomImage

        }

    constructor(
            address: String,
            name: String,
            longitude: Double?,
            latitude: Double?,
            placeUrl: String
    ) {
        this.address = address
        this.name = name
        this.longitude = longitude
        this.latitude = latitude
        this.placeUrl = placeUrl
        this.imageUrl = imageUrlByName
    }

    companion object {

        internal val IMAGE_HOST = "https://alone.social"
        internal val DEFAULT_IMAGE = "https://alone.social/cafe/random/0.jpg"
    }
}
