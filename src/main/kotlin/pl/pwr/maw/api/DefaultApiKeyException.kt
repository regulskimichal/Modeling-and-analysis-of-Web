package pl.pwr.maw.api

import pl.pwr.maw.commons.DomainException

class DefaultApiKeyException(
    id: Any,
    cause: Throwable? = null
) : DomainException(
    "${ApiKey::class.simpleName} with id = $id has parameter default_key = true, so it cannot be removed",
    cause
)
