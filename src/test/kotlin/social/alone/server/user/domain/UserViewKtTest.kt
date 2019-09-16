package social.alone.server.user.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test


class UserViewKtTest {

    @Test
    fun test_image_default_url() {
        val user = User("email@email.com", "password", "name");

        assertThat(user.view()).isNotNull
        assertThat(user.view().imageUrl).isNotEmpty()

    }
}
