package pl.pwr.maw.settings

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import pl.pwr.maw.commons.DomainException

@ResponseStatus(HttpStatus.BAD_REQUEST)
class InvalidCronExpressionException(
    message: String? = null,
    cause: Throwable? = null
) : DomainException(message, cause)
