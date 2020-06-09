package pl.pwr.maw.model

enum class ApiType {
    WEB_PAGE_TEST, PAGE_SPEED;

    companion object {
        const val WEB_PAGE_TEST_NAME = "WEB_PAGE_TEST"
        const val PAGE_SPEED_NAME = "PAGE_SPEED"

        fun forName(name: String?) = values().find { it.name.equals(name, true) }
    }

}
