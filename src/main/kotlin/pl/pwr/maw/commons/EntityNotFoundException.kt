package pl.pwr.maw.commons

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import kotlin.reflect.KClass

@ResponseStatus(HttpStatus.NOT_FOUND)
class EntityNotFoundException(
    clazz: KClass<*>,
    id: Any?,
    cause: Throwable? = null
) : DomainException(getMessage(clazz, id), cause) {
    companion object {
        private fun getMessage(clazz: KClass<*>, id: Any?): String? {
            return "Resource ${clazz.simpleName} with id = $id has not been found"
        }
    }
}

@Suppress("FunctionName")
inline fun <reified T> EntityNotFoundException(id: Any?, cause: Throwable? = null): EntityNotFoundException {
    return EntityNotFoundException(T::class, id, cause)
}
