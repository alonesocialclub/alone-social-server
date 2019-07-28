package social.alone.server.location


import lombok.AllArgsConstructor
import lombok.Getter
import lombok.NoArgsConstructor

@Getter
@AllArgsConstructor
@NoArgsConstructor
data class LocationDto(
        var address: String = "",
        var name: String = "",
        var longitude: Double,
        var latitude: Double,
        var placeUrl: String=""
) {


    fun buildLocation(): Location {
        return Location(address, name, longitude, latitude, placeUrl)
    }
}