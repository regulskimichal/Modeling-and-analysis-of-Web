package pl.pwr.maw.api

import pl.pwr.maw.commons.DomainException
import pl.pwr.maw.model.ApiKey

class DefaultApiKeyException(
    id: Any,
    cause: Throwable? = null
) : DomainException(
    "${ApiKey::class.simpleName} with id = $id has parameter default_key = true, so it cannot be removed",
    cause
)
