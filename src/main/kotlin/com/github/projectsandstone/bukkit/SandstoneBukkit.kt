/**
 *      SandstoneBukkit - Bukkit implementation of SandstoneCommon
 *
 *         The MIT License (MIT)
 *
 *      Copyright (c) 2016 Sandstone <https://github.com/ProjectSandstone/>
 *      Copyright (c) contributors
 *
 *
 *      Permission is hereby granted, free of charge, to any person obtaining a copy
 *      of this software and associated documentation files (the "Software"), to deal
 *      in the Software without restriction, including without limitation the rights
 *      to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *      copies of the Software, and to permit persons to whom the Software is
 *      furnished to do so, subject to the following conditions:
 *
 *      The above copyright notice and this permission notice shall be included in
 *      all copies or substantial portions of the Software.
 *
 *      THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *      IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *      FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *      AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *      LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *      OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *      THE SOFTWARE.
 */
package com.github.projectsandstone.bukkit

import com.github.projectsandstone.api.Sandstone
import com.github.projectsandstone.api.constants.SandstonePlugin
import com.github.projectsandstone.bukkit.events.InitializationEventImpl
import com.github.projectsandstone.bukkit.events.PostInitializationEventImpl
import com.github.projectsandstone.bukkit.events.PreInitializationEventImpl
import com.github.projectsandstone.bukkit.logger.BukkitLogger
import com.github.projectsandstone.bukkit.logger.BukkitLoggerFactory
import com.github.projectsandstone.common.SandstoneInit
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import java.nio.file.Files
import java.nio.file.Paths

/**
 * Created by jonathan on 22/08/16.
 */
class SandstoneBukkit : JavaPlugin() {

    var taskId: Int = -1


    init {
        instance = this
        this.createDir()

        SandstoneInit.initGame(BukkitGame)
        SandstoneInit.initLogger(BukkitLogger(this.logger))
        SandstoneInit.initLoggerFactory(BukkitLoggerFactory)

        SandstoneInit.loadPlugins(pluginsDir)
        SandstoneInit.startPlugins()
    }

    override fun onLoad() {
        this.preInitialize()
    }

    override fun onEnable() {
        taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(this, {
            if(taskId == -1) {
                Sandstone.logger.warn("Invalid Task Id = (-1)")
            }
            if (Bukkit.getWorld("world") != null) {
                this.worldLoaded()
                Bukkit.getScheduler().cancelTask(taskId)
                taskId = -1
            }
        }, 5 * 20L, 5 * 20L)
        this.initialize()
    }


    fun worldLoaded() {
        this.postInitialize()
    }

    fun preInitialize() {
        // TODO: Dispatch async
        Sandstone.game.eventManager.dispatch(PreInitializationEventImpl(), SandstonePlugin)
    }

    fun initialize() {
        Sandstone.game.eventManager.dispatch(InitializationEventImpl(), SandstonePlugin)
    }

    fun postInitialize() {
        Sandstone.game.eventManager.dispatch(PostInitializationEventImpl(), SandstonePlugin)
    }

    companion object {
        val pluginsDir = Paths.get(".", "Sandstone", "plugins")
        private lateinit var instance: SandstoneBukkit

        fun getSandstonePluginInstance() = instance
    }

    private fun createDir() {
        Files.createDirectories(pluginsDir)
    }
}