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
package com.github.projectsandstone.bukkit.scheduler

import com.github.projectsandstone.api.scheduler.SubmittedTask
import com.github.projectsandstone.api.scheduler.Task
import com.github.projectsandstone.bukkit.SandstoneBukkit
import com.github.projectsandstone.bukkit.util.MinecraftTicks
import com.github.projectsandstone.common.scheduler.SandstoneScheduler
import org.bukkit.Bukkit
import org.bukkit.scheduler.BukkitTask
import java.time.Duration

/**
 * Created by jonathan on 27/08/16.
 */
object BukkitScheduler : SandstoneScheduler() {
    override fun submit(task: Task): SubmittedTask {
        val interval = task.interval
        val isRepeating = interval != Duration.ZERO

        val isAsync = task.isAsync
        val bukkitTask: BukkitTask

        if (isRepeating) {
            if (isAsync) {
                bukkitTask = Bukkit.getScheduler().runTaskTimerAsynchronously(
                        SandstoneBukkit.getSandstonePluginInstance(),
                        task.runnable,
                        task.delay.toMinecraftTicks(),
                        task.interval.toMinecraftTicks())
            } else {
                bukkitTask = Bukkit.getScheduler().runTaskTimer(
                        SandstoneBukkit.getSandstonePluginInstance(),
                        task.runnable,
                        task.delay.toMinecraftTicks(),
                        task.interval.toMinecraftTicks())
            }
        } else {
            if (isAsync) {
                bukkitTask = Bukkit.getScheduler().runTaskLaterAsynchronously(
                        SandstoneBukkit.getSandstonePluginInstance(),
                        task.runnable,
                        task.delay.toMinecraftTicks())
            } else {
                bukkitTask = Bukkit.getScheduler().runTaskLater(
                        SandstoneBukkit.getSandstonePluginInstance(),
                        task.runnable,
                        task.delay.toMinecraftTicks())
            }
        }


        return BukkitSubmittedTask(task, bukkitTask)
    }

    internal fun Duration.toMinecraftTicks(): Long {
        return MinecraftTicks.milliToTicks(this.toMillis())
    }
}