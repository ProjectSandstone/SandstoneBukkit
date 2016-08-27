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
package com.github.projectsandstone.bukkit.logger

import com.github.projectsandstone.api.logging.LogLevel
import java.io.PrintWriter
import java.io.StringWriter
import java.util.logging.Level

/**
 * Created by jonathan on 22/08/16.
 */
fun LogLevel.toJava(): Level {
    when (this) {
        LogLevel.DEBUG -> return Level.FINE
        LogLevel.INFO -> return Level.INFO
        LogLevel.WARN -> return Level.WARNING
        LogLevel.EXCEPTION -> return Level.SEVERE
        LogLevel.ERROR -> return Level.SEVERE
    }
}

fun Throwable.printStackTrace(additional: String? = null, func: (String) -> Unit) {
    val strWriter = StringWriter()
    val writer = PrintWriter(strWriter)

    if(additional != null) {
        writer.println("Message: $additional")
    }

    this.printStackTrace(writer = writer)

    func(strWriter.toString())
}

