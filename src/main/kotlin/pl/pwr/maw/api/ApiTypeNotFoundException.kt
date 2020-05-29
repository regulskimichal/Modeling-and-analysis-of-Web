package pl.pwr.maw.api

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import pl.pwr.maw.commons.DomainException

@ResponseStatus(HttpStatus.BAD_REQUEST)
class ApiTypeNotFoundException(
    apiTypeName: String,
    cause: Throwable? = null
) : DomainException("${ApiKeyType::class.simpleName} = $apiTypeName is invalid", cause)
