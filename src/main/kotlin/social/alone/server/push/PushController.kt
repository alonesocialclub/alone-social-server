package social.alone.server.push

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import social.alone.server.auth.oauth2.user.CurrentUser
import social.alone.server.user.User

@Controller
@RequestMapping(value = ["/api/push"])
class PushController {

    @Autowired
    lateinit var fcmTokenRegisterSvc: FcmTokenRegisterSvc;

    @PostMapping("/tokens")
    fun registerToken(
            @RequestBody req: FcmTokenRequest,
            @CurrentUser user: User?
    ): ResponseEntity<*> {

        val fcmToken = req.fcmToken?.let { fcmTokenRegisterSvc.register(it) }
        println("========$fcmToken")
        return ResponseEntity.noContent().build<Any>()
    }
}
