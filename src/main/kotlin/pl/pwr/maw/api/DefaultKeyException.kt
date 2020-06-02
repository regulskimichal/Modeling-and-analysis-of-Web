package pl.pwr.maw.api

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import pl.pwr.maw.commons.DomainException
import pl.pwr.maw.model.ApiKey

@ResponseStatus(HttpStatus.BAD_REQUEST)
class DefaultKeyException(
    message: String? = null,
    cause: Throwable? = null
) : DomainException(message, cause) {

    companion object {
        fun onRemove(id: Any, cause: Throwable? = null): DefaultKeyException {
            return DefaultKeyException(
                "${ApiKey::class.simpleName} with id = $id has parameter defaultKey = true, so it cannot be removed",
                cause
            )
        }

        fun onUpdate(cause: Throwable? = null): DefaultKeyException {
            return DefaultKeyException("Provided value defaultKey is illegal", cause)
        }
    }

}
