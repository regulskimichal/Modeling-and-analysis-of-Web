package pl.pwr.maw.utils

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.convertValue
import javax.json.JsonPatch
import javax.json.JsonStructure

inline fun <reified T : Any> JsonPatch.applyOn(objectMapper: ObjectMapper, value: T): T {
    val json = objectMapper.convertValue<JsonStructure>(value)
    return objectMapper.convertValue(apply(json))
}
