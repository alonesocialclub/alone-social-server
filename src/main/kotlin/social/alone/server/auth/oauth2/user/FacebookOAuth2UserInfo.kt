package social.alone.server.auth.oauth2.user

class FacebookOAuth2UserInfo(attributes: Map<String, Any>) : OAuth2UserInfo(attributes) {

    private var s3ImageUrl: String? = null

    override val id: String
        get() = attributes["id"] as String

    override val name: String
        get() = attributes["name"] as String

    override val email: String
        get() = attributes["email"] as String

    override val imageUrl: String
        get() {
            if (s3ImageUrl != null) {
                return s3ImageUrl as String
            }
            if (attributes.containsKey("picture")) {
                val pictureObj = attributes["picture"] as Map<String, Any>
                if (pictureObj.containsKey("data")) {
                    val dataObj = pictureObj["data"] as Map<String, Any>
                    if (dataObj.containsKey("url")) {
                        return dataObj["url"] as String
                    }
                }
            }
            return "foobar"
        }

    fun setS3ImageUrl(s3ImageUrl: String) {
        this.s3ImageUrl = s3ImageUrl
    }
}
