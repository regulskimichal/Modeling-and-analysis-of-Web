package pl.pwr.maw

import org.springframework.http.HttpStatus
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.server.ResponseStatusException

@RestControllerAdvice
class ExceptionHandler {

    @ExceptionHandler(IllegalArgumentException::class)
    fun illegalArgumentExceptionHandler(
        ex: IllegalArgumentException,
        request: ServerHttpRequest
    ) {
        throw ResponseStatusException(HttpStatus.BAD_REQUEST, ex.message)
    }

    @ExceptionHandler(RuntimeException::class)
    fun runtimeExceptionHandler(
        ex: RuntimeException,
        request: ServerHttpRequest
    ) {
        throw ResponseStatusException(HttpStatus.BAD_REQUEST, ex.message)
    }

}
