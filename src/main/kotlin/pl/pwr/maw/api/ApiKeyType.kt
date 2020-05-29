package pl.pwr.maw.api

enum class ApiKeyType {
    WEB_PAGE_TEST, PAGE_SPEED;

    companion object {
        val values: Collection<ApiKeyType> = values().asList()
    }

}
