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
package com.github.projectsandstone.bukkit.adapter.entity.living.player

import com.github.jonathanxd.adapter.annotations.*
import com.github.jonathanxd.adapter.info.CallInfo
import com.github.projectsandstone.api.entity.living.player.Player
import com.github.projectsandstone.api.text.Text
import com.github.projectsandstone.bukkit.adapter.entity.EntityAdapter
import com.github.projectsandstone.bukkit.adapter.text.channel.MessageReceiverAdapter
import com.github.projectsandstone.bukkit.converter.TextConverter

@AdapterEnv
@Adapter(target = org.bukkit.entity.Player::class, interfaces = arrayOf(Player::class),
        mixinGeneratedClasses = arrayOf(MessageReceiverAdapter::class, UserAdapter::class, EntityAdapter::class))
class PlayerAdapter : Player, MessageReceiverAdapter, UserAdapter {


    @Invoke(method = "kickPlayer", parameters = arrayOf(Parameter(type = String::class, argumentProvider = ValueProvider(
            providerClass = Companion::class,
            returnType = String::class,
            providerMethod = "defaultReason"
    ))))
    override fun kick() {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    @Invoke(method = "kickPlayer", parameters = arrayOf(Parameter(type = String::class, converter = Converter(registryId = TextConverter.id_))))
    override fun kick(reason: Text) {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    companion object {
        @JvmStatic
        fun defaultReason(callInfo: CallInfo) : String = "Unknown Reason!"
    }
}