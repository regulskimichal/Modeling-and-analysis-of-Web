package pl.pwr.maw.utils

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import com.fasterxml.jackson.datatype.jsr353.JsonValueDeserializer
import java.io.IOException
import javax.json.Json
import javax.json.JsonArray
import javax.json.JsonPatch

class JsonPatchDeserializer(
    private val jsonValueDeserializer: JsonValueDeserializer
) : StdDeserializer<JsonPatch?>(JsonPatch::class.java) {

    override fun deserialize(
        jsonParser: JsonParser,
        deserializationContext: DeserializationContext
    ): JsonPatch {
        val deserialize = jsonValueDeserializer.deserialize(jsonParser, deserializationContext)
        if (deserialize is JsonArray) {
            return Json.createPatch(deserialize)
        } else {
            throw IOException()
        }
    }

}
