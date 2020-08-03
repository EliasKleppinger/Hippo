package com.github.eliaskleppinger.hippo.event

import com.github.eliaskleppinger.hippo.Hippo
import com.google.common.reflect.ClassPath
import org.bukkit.Bukkit
import org.bukkit.event.Event
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.plugin.EventExecutor

class EventManager : Listener {

    private lateinit var eventNames: HashMap<String, Class<Event>>

    init {
        this.eventNames = HashMap()
        val classPath = ClassPath.from(Hippo.instance.getLoader())
        for(info in classPath.getTopLevelClasses()) {
            if(!info.simpleName.toLowerCase().endsWith("event")) {
                continue
            }
            eventNames.put(info.simpleName.toLowerCase().replace("event", ""), info.load() as Class<Event>);
        }
    }

    public fun on(eventName: String, consumer: (Any) -> Unit) {
        val eventClass: Class<Event>? = eventNames.get(eventName.toLowerCase());
        val eventExecutor = EventExecutor() { listener: Listener, event: Event ->
            consumer(event)
        }
        if (eventClass != null) {
            Bukkit.getPluginManager().registerEvent(eventClass, this, EventPriority.NORMAL, eventExecutor, Hippo.instance)
        }
    }

}