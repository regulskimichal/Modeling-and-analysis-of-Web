package pl.pwr.maw.commons.events

import org.springframework.context.ApplicationEvent

class DeregisterEvent(source: Any, val settingsId: Long) : ApplicationEvent(source)
