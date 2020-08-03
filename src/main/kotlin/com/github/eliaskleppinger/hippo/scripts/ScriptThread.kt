package com.github.eliaskleppinger.hippo.scripts

import com.eclipsesource.v8.*
import com.eclipsesource.v8.utils.V8ObjectUtils
import com.github.eliaskleppinger.hippo.Hippo
import io.js.J2V8Classes.V8JavaClasses
import org.bukkit.Bukkit
import org.bukkit.scheduler.BukkitRunnable
import java.io.File
import java.lang.Exception
import java.lang.reflect.Modifier

class ScriptThread(private val scriptFile: File) : Thread() {
    private lateinit var nodeJS: NodeJS;

    override fun run() {
        nodeJS = NodeJS.createNodeJS()
        V8JavaClasses.injectClassHelper(nodeJS.runtime, scriptFile.nameWithoutExtension)
        nodeJS.exec(scriptFile)
        while (nodeJS.isRunning()) {
            nodeJS.handleMessage()
        }
        try {
            nodeJS.release();
        } catch(e: Exception) {

        }
    }


}