package pl.pwr.maw.api

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import pl.pwr.maw.commons.DomainException
import pl.pwr.maw.model.ApiKey

@ResponseStatus(HttpStatus.BAD_REQUEST)
class DefaultApiKeyException(
    id: Any,
    cause: Throwable? = null
) : DomainException(
    "${ApiKey::class.simpleName} with id = $id has parameter default_key = true, so it cannot be removed",
    cause
)
