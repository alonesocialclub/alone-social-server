package social.alone.server.location


import lombok.AllArgsConstructor
import lombok.Getter
import lombok.NoArgsConstructor

@Getter
@AllArgsConstructor
@NoArgsConstructor
class LocationDto {

    val address: String? = null
    val name: String? = null
    val longitude: Double? = null
    val latitude: Double? = null
    val placeUrl: String? = null

    fun buildLocation(): Location {
        return Location(address, name, longitude, latitude, placeUrl)
    }
}