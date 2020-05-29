package pl.pwr.maw.api

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

@Component
class ApiKeyValidator(
    private val apiKeyRepository: ApiKeyRepository
) : ConstraintValidator<ValidApiKey, ApiKey> {
    override fun isValid(value: ApiKey?, context: ConstraintValidatorContext?): Boolean {
        if (value?.id == null) {
            return false
        }

        val oldApiKey = apiKeyRepository.findByIdOrNull(value.id)
        return when {
            oldApiKey == null -> false
            oldApiKey.type != value.type -> false
            oldApiKey.defaultKey && !value.defaultKey -> false
            else -> true
        }
    }
}
