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
import com.github.projectsandstone.api.event.SandstoneEventFactory
import com.github.projectsandstone.bukkit.logger.BukkitLogger
import com.github.projectsandstone.bukkit.logger.BukkitLoggerFactory
import com.github.projectsandstone.bukkit.logger.LoggerFixer
import com.github.projectsandstone.common.SandstoneInit
import com.github.projectsandstone.common.adapter.Adapters
import com.github.projectsandstone.common.adapter.SandstoneAdapters
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import java.nio.file.Files
import java.nio.file.Paths

/**
 * Created by jonathan on 22/08/16.
 */
class SandstoneBukkit : JavaPlugin() {

    var taskId: Int = -1
    private var serverInstanceAvailable: Boolean = false

    init {
        instance = this
        this.createDir()

        this.logger.info("Loading Sandstone adapters...")

        AdapterRegistry.registerAdapters()
        // Save
        Adapters.adapters.save(SandstoneAdapters.adaptersDir)

        val adaptedClasses = Adapters.adapters.adapterEnvironment.adaptedClasses
        val loadedAdapters = adaptedClasses.size

        this.logger.info("$loadedAdapters adapters loaded!")

        val names = adaptedClasses.map { it.adapterClass.simpleName }.joinToString { it }

        this.logger.info("Adapters loaded: $names!")

        SandstoneInit.initConsts()
        SandstoneInit.initPath(Paths.get("./Sandstone/"))
        SandstoneInit.initGame(BukkitGame)
        SandstoneInit.initLogger(BukkitLogger(LoggerFixer(this.logger)))
        SandstoneInit.initLoggerFactory(BukkitLoggerFactory)

        SandstoneInit.loadPlugins(pluginsDir)
        SandstoneInit.startPlugins()

    }

    fun isServerAvailable(): Boolean {
        return this.serverInstanceAvailable
    }

    override fun onLoad() {
        this.preInitialize()
    }

    override fun onEnable() {
        this.initialize()

        taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(this, {
            if (taskId == -1) {
                Sandstone.logger.warn("Invalid Task Id = (-1)")
            }

            if (Bukkit.getWorld("world") != null) {
                this.worldLoaded()

                this.serverStarting()
                this.serverInstanceAvailable = true
                this.serverStarted()

                Bukkit.getScheduler().cancelTask(taskId)
                taskId = -1
            }
        }, 1 * 20L, 1 * 20L)
    }

    override fun onDisable() {
        Bukkit.getServer()
        this.serverStopping()
        this.serverStopped()
        // TODO: Fix plugin reload
    }

    fun worldLoaded() {
        this.postInitialize()
    }

    fun serverStarting() {
        Sandstone.game.eventManager.dispatch(SandstoneEventFactory.createServerStartingEvent(), SandstonePlugin)
    }

    fun serverStarted() {
        Sandstone.game.eventManager.dispatch(SandstoneEventFactory.createServerStartedEvent(), SandstonePlugin)
    }

    fun preInitialize() {
        Sandstone.game.eventManager.dispatchAsync(SandstoneEventFactory.createPreInitializationEvent(), SandstonePlugin)
    }

    fun initialize() {
        Sandstone.game.eventManager.dispatch(SandstoneEventFactory.createInitializationEvent(), SandstonePlugin)
    }

    fun postInitialize() {
        Sandstone.game.eventManager.dispatch(SandstoneEventFactory.createPostInitializationEvent(), SandstonePlugin)
    }

    fun serverStopping() {
        Sandstone.game.eventManager.dispatch(SandstoneEventFactory.createServerStoppingEvent(), SandstonePlugin)
    }

    fun serverStopped() {
        Sandstone.game.eventManager.dispatch(SandstoneEventFactory.createServerStoppedEvent(), SandstonePlugin)
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