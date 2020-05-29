package pl.pwr.maw.commons

open class DomainException(message: String? = null, cause: Throwable? = null) : RuntimeException(message, cause)
