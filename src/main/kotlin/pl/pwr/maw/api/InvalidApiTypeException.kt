package pl.pwr.maw.api

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import pl.pwr.maw.commons.DomainException

@ResponseStatus(HttpStatus.BAD_REQUEST)
open class InvalidApiTypeException(
    message: String? = null,
    cause: Throwable? = null
) : DomainException(message, cause)
