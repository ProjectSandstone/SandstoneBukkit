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
package com.github.projectsandstone.bukkit.util

import java.util.concurrent.TimeUnit

/**
 * Created by jonathan on 27/08/16.
 */
object MinecraftTicks {

    val MILLI_PER_TICK = 50
    val NANO_PER_TICK = 1000000 * MILLI_PER_TICK

    fun unitToTick(time: Long, timeUnit: TimeUnit) : Long {
        return this.milliToTicks(timeUnit.toMillis(time))
    }

    fun milliToTicks(milliseconds: Long) : Long {
        return Math.round(milliseconds.toDouble() / MILLI_PER_TICK.toDouble())
    }

    fun nanoToTicks(nanoseconds: Long) : Long {
        return Math.round(nanoseconds.toDouble() / NANO_PER_TICK.toDouble())
    }

}