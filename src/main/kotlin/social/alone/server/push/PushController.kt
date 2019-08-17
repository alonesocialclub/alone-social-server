package social.alone.server.push

import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import social.alone.server.auth.oauth2.user.CurrentUser
import social.alone.server.push.dto.FcmTokenRequest
import social.alone.server.user.domain.User

@Controller
@RequestMapping(value = ["/api/push"])
class PushController (
        val fcmTokenRegisterSvc: FcmTokenRegisterSvc,
        val notificationSendSvc: NotificationSendSvc
){

    @PostMapping("/tokens")
    fun registerToken(
            @RequestBody req: FcmTokenRequest,
            @CurrentUser user: User?
    ): ResponseEntity<*> {
        req.fcmToken?.let {
            fcmTokenRegisterSvc.register(it, user)
        }
        return ResponseEntity.noContent().build<Any>()
    }

    @PostMapping("/notices")
    fun notices(
            @CurrentUser user: User?,
            @RequestBody req: Request
    ): ResponseEntity<*> {
        if (user != null && user.isAdmin){
            notificationSendSvc.noticeAll(req.message)
        }
        return ResponseEntity.noContent().build<Any>()
    }

    data class Request(val message: String)
}
