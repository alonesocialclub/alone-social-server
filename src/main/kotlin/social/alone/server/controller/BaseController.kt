package social.alone.server.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.Errors
import social.alone.server.validate.ErrorsResource

open class BaseController {

    protected fun BadRequest(errors: Errors): ResponseEntity<*> {
        return ResponseEntity.badRequest().body(ErrorsResource(errors))
    }

    protected fun NotFound(): ResponseEntity<*> {
        return ResponseEntity.notFound().build<Any>()
    }

    protected fun forbidden(): ResponseEntity<*> {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build<Any>()
    }
}
