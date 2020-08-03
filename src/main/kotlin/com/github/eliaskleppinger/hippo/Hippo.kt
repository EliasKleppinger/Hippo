package com.github.eliaskleppinger.hippo

import com.eclipsesource.v8.NodeJS
import com.eclipsesource.v8.V8
import com.eclipsesource.v8.V8Object
import com.github.eliaskleppinger.hippo.event.EventManager
import com.github.eliaskleppinger.hippo.scripts.ScriptThread
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import java.awt.Event
import java.io.File

class Hippo : JavaPlugin() {

    companion object {
        lateinit var instance: Hippo;
        lateinit var eventManager: EventManager
    }

    private lateinit var jsDirectory: File;
    private lateinit var indexFile: File

    override fun onEnable() {
        instance = this;
        eventManager = EventManager()
        jsDirectory = File("javascript");
        jsDirectory.mkdirs();
        indexFile = File("javascript/index.js")
        indexFile.createNewFile()
        ScriptThread(indexFile).start()
    }

    public fun getLoader(): ClassLoader {
        return classLoader
    }

}