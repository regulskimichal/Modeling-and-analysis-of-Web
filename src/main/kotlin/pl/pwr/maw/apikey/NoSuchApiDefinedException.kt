package pl.pwr.maw.apikey

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import pl.pwr.maw.DomainException

@ResponseStatus(HttpStatus.BAD_REQUEST)
class NoSuchApiDefinedException : DomainException("API name is incorrect")
