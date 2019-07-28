package social.alone.server.auth.oauth2.user

abstract class OAuth2UserInfo(attributes: Map<String, Any>) {
    var attributes: Map<String, Any>
        protected set

    abstract val id: String

    abstract val name: String

    abstract val email: String

    abstract val imageUrl: String

    init {
        this.attributes = attributes
    }
}
