package pl.pwr.maw.utils

import com.fasterxml.jackson.databind.*
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer
import com.fasterxml.jackson.databind.module.SimpleDeserializers
import com.fasterxml.jackson.databind.type.CollectionType
import com.fasterxml.jackson.databind.type.MapType
import com.fasterxml.jackson.datatype.jsr353.JSR353Module
import com.fasterxml.jackson.datatype.jsr353.JsonValueDeserializer
import com.fasterxml.jackson.datatype.jsr353.JsonValueSerializer
import javax.json.JsonArray
import javax.json.JsonObject
import javax.json.JsonPatch
import javax.json.JsonValue

class JSR374Module : JSR353Module() {

    val jsonValueDeser = JsonValueDeserializer(_builderFactory)
    val jsonPatchDeser = JsonPatchDeserializer(jsonValueDeser)

    init {
        addSerializer(JsonValue::class.java, JsonValueSerializer())
        setDeserializers(object : SimpleDeserializers() {
            override fun findBeanDeserializer(
                type: JavaType,
                config: DeserializationConfig, beanDesc: BeanDescription
            ): JsonDeserializer<*>? {
                return when {
                    JsonValue::class.java.isAssignableFrom(type.rawClass) -> jsonValueDeser
                    JsonPatch::class.java.isAssignableFrom(type.rawClass) -> jsonPatchDeser
                    else -> null
                }
            }

            override fun findCollectionDeserializer(
                type: CollectionType,
                config: DeserializationConfig, beanDesc: BeanDescription,
                elementTypeDeserializer: TypeDeserializer,
                elementDeserializer: JsonDeserializer<*>?
            ): JsonDeserializer<*>? {
                return when {
                    JsonArray::class.java.isAssignableFrom(type.rawClass) -> jsonValueDeser
                    else -> null
                }
            }

            override fun findMapDeserializer(
                type: MapType,
                config: DeserializationConfig, beanDesc: BeanDescription,
                keyDeserializer: KeyDeserializer,
                elementTypeDeserializer: TypeDeserializer,
                elementDeserializer: JsonDeserializer<*>?
            ): JsonDeserializer<*>? {
                return when {
                    JsonObject::class.java.isAssignableFrom(type.rawClass) -> jsonValueDeser
                    else -> null
                }
            }
        })
    }

}
