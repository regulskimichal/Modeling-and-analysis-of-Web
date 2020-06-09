package pl.pwr.maw.model

enum class ConnectivityProfile(
    val value: String
) {
    DSL("DSL"),
    CABLE("Cable"),
    FIOS("FIOS"),
    DIAL("Dial"),
    EDGE("Edge"),
    _2G("2G"),
    _3G_SLOW("3GSlow"),
    _3G("3G"),
    _3G_FAST("3GFast"),
    _4G("4G"),
    LTE("LTE"),
    NATIVE("Native")
}
