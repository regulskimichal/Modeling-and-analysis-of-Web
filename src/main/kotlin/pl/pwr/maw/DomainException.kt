package pl.pwr.maw

open class DomainException(message: String? = null, cause: Throwable? = null) : RuntimeException(message, cause)
