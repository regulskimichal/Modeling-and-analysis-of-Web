package pl.pwr.maw.model

enum class ApiType {
    WEB_PAGE_TEST, PAGE_SPEED;

    companion object {
        val values: Collection<ApiType> = values().asList()
        const val WEB_PAGE_TEST_NAME = "WEB_PAGE_TEST"
        const val PAGE_SPEED_NAME = "PAGE_SPEED"
    }

}
