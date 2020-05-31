package pl.pwr.maw.events

import org.springframework.context.ApplicationEvent
import pl.pwr.maw.model.Setting

class RegisterEvent(source: Any, val setting: Setting) : ApplicationEvent(source)
