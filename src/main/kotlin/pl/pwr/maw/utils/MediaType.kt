package pl.pwr.maw.utils

@Suppress("MemberVisibilityCanBePrivate")
class MediaType(type: String) : org.springframework.http.MediaType(type) {

    companion object {

        const val APPLICATION_JSON_PATCH_VALUE = "application/json-patch+json"

        @JvmField
        val APPLICATION_JSON_PATCH = valueOf(APPLICATION_JSON_PATCH_VALUE)

        const val APPLICATION_MERGE_PATCH_VALUE = "application/merge-patch+json"

        @JvmField
        val APPLICATION_MERGE_PATCH = valueOf(APPLICATION_MERGE_PATCH_VALUE)

    }

}
