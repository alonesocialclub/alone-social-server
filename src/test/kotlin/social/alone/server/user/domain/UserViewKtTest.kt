package social.alone.server.user.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import social.alone.server.makeUser


class UserViewKtTest {

    @Test
    fun test_image_default_url() {
        val user = makeUser()

        assertThat(user.view()).isNotNull

    }
}
