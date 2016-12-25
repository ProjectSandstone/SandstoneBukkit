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

import com.github.projectsandstone.api.Platform
import com.github.projectsandstone.api.Server
import com.github.projectsandstone.api.scheduler.Scheduler
import com.github.projectsandstone.api.service.ServiceManager
import com.github.projectsandstone.api.util.edition.GameEdition
import com.github.projectsandstone.api.util.edition.GameEditions
import com.github.projectsandstone.bukkit.scheduler.BukkitScheduler
import com.github.projectsandstone.bukkit.service.BukkitServiceManager
import com.github.projectsandstone.common.AbstractGame
import com.github.projectsandstone.common.adapter.Adapters
import org.bukkit.Bukkit
import java.nio.file.Path
import java.nio.file.Paths

/**
 * Created by jonathan on 22/08/16.
 */
object BukkitGame : AbstractGame() {

    override val gamePath: Path = Paths.get(".")
    override val savePath: Path = Paths.get(".")
    override val serviceManager: ServiceManager = BukkitServiceManager()
    override val scheduler: Scheduler = BukkitScheduler
    override val platform: Platform = BukkitPlatform
    override val edition: GameEdition = GameEditions.PC

    override val server: Server
        get() {
            if(!SandstoneBukkit.getSandstonePluginInstance().isServerAvailable()) {
                throw IllegalStateException("Server instance is not available yet!")
            } else {
                return Adapters.adapters.adaptUnchecked(org.bukkit.Server::class.java, Bukkit.getServer(), Server::class.java)
            }
        }
}