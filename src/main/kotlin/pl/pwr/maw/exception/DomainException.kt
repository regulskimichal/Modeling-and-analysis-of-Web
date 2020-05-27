package pl.pwr.maw.exception

open class DomainException(message: String? = null, cause: Throwable? = null) : RuntimeException(message, cause)
