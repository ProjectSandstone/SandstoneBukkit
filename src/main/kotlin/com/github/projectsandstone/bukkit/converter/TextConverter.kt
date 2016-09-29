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
package com.github.projectsandstone.bukkit.converter

import com.github.jonathanxd.adapter.info.CallInfo
import com.github.jonathanxd.adapter.spec.ConverterSpec
import com.github.projectsandstone.api.text.Text
import com.github.projectsandstone.api.text.style.TextColor
import com.github.projectsandstone.api.text.style.TextFormat
import com.github.projectsandstone.common.adapter.RegistryCandidate
import com.github.projectsandstone.common.adapter.annotation.RegistryTypes
import org.bukkit.ChatColor

//@AdapterRegistry(id = "TEXT_TO_STRING_CONVERTER", type = Type.CONVERTER)
object TextConverter : RegistryCandidate<ConverterSpec> {

    const val id_: String = "TEXT_TO_STRING_CONVERTER"

    override val id: String = id_
    override val spec = ConverterSpec(TextConverter::class.java, "convert", String::class.java, arrayOf(Text::class.java))
    override val registryType = RegistryTypes.Converter(Text::class.java, String::class.java)

    @JvmStatic
    fun convert(callInfo: CallInfo, text: Text): String {
        val builder = StringBuilder()

        this.convertAll(callInfo, text, builder)

        return builder.toString()
    }

    @JvmStatic
    fun convertAll(callInfo: CallInfo, text: Text, builder: StringBuilder) {
        this.convertTextIndividual(callInfo, text, builder)
        val parent = text.parent

        if (parent != null && parent.size > 0) {
            parent.forEach {
                this.convertAll(callInfo, it, builder)
            }
        }
    }

    @JvmStatic
    fun convertTextIndividual(callInfo: CallInfo, text: Text, builder: StringBuilder) {
        builder.append(this.convertColor(callInfo, text.color))
        builder.append(this.convertFormat(callInfo, text.format))
        builder.append(text.content)
    }

    @JvmStatic
    fun convertColor(callInfo: CallInfo, textColor: TextColor): String {
        return ChatColor.valueOf(textColor.name.toUpperCase()).toString()
    }

    @JvmStatic
    fun convertFormat(callInfo: CallInfo, textFormat: TextFormat): String {
        val builder = StringBuilder()

        if (textFormat.bold)
            builder.append(ChatColor.BOLD)

        if (textFormat.italic)
            builder.append(ChatColor.ITALIC)

        if (textFormat.obfuscated)
            builder.append(ChatColor.MAGIC)

        if (textFormat.strikeThrough)
            builder.append(ChatColor.STRIKETHROUGH)

        if (textFormat.underline)
            builder.append(ChatColor.UNDERLINE)

        return builder.toString()
    }
}