package pl.pwr.maw.events

import org.springframework.context.ApplicationEvent

class DeregisterEvent(source: Any, val settingsId: Long) : ApplicationEvent(source)
