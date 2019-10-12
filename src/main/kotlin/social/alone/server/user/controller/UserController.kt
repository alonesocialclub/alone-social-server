package social.alone.server.user.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import social.alone.server.controller.BaseController
import social.alone.server.user.domain.User
import social.alone.server.user.domain.UserView
import social.alone.server.user.domain.view

@RestController
class UserController : BaseController() {

    @GetMapping("/users/{id}")
    fun getUser(@PathVariable("id") user: User): UserView {
        return user.view()
    }

}
