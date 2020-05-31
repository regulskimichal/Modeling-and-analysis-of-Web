package pl.pwr.maw.api

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import pl.pwr.maw.commons.DomainException
import pl.pwr.maw.model.ApiType

@ResponseStatus(HttpStatus.BAD_REQUEST)
class ApiTypeNotFoundException(
    apiTypeName: String,
    cause: Throwable? = null
) : DomainException("${ApiType::class.simpleName} = $apiTypeName is invalid", cause)
