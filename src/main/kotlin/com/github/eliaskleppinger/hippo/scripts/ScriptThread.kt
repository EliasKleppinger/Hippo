package com.github.eliaskleppinger.hippo.scripts

import com.eclipsesource.v8.*
import com.eclipsesource.v8.utils.V8ObjectUtils
import com.github.eliaskleppinger.hippo.Hippo
import org.bukkit.scheduler.BukkitRunnable
import java.io.File

class ScriptThread(private val scriptFile: File) : Thread() {
    private lateinit var nodeJS: NodeJS;

    override fun run() {
        nodeJS = NodeJS.createNodeJS()
        nodeJS.exec(scriptFile)
        while (nodeJS.isRunning()) {
            nodeJS.handleMessage()
        }
        nodeJS.release()
    }

}