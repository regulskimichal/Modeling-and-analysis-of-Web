package pl.pwr.maw.commons.events

import org.springframework.context.ApplicationEvent
import pl.pwr.maw.settings.Setting

class RegisterEvent(source: Any, val setting: Setting) : ApplicationEvent(source)
