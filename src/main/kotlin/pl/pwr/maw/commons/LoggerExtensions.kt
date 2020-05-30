package pl.pwr.maw.commons

import org.slf4j.Logger
import org.slf4j.LoggerFactory

inline fun <reified T> logger(): Lazy<Logger> = lazy { LoggerFactory.getLogger(T::class.java) }
