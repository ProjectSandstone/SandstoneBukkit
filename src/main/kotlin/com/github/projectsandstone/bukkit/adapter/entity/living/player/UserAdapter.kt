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

import com.github.jonathanxd.adapter.annotations.Adapter
import com.github.jonathanxd.adapter.annotations.AdapterEnv
import com.github.jonathanxd.adapter.annotations.Invoke
import com.github.jonathanxd.adapter.info.CallInfo
import com.github.projectsandstone.api.Sandstone
import com.github.projectsandstone.api.entity.living.player.Player
import com.github.projectsandstone.api.entity.living.player.User
import java.util.*

@AdapterEnv
@Adapter(target = org.bukkit.OfflinePlayer::class, interfaces = arrayOf(User::class))
interface UserAdapter : User {
    override val uniqueId: UUID
        @Invoke(method = "getUniqueId")
        get() = throw UnsupportedOperationException()

    override val isOnline: Boolean
        @Invoke(method = "isOnline")
        get() = throw UnsupportedOperationException()

    override val name: String
        @Invoke(method = "getName")
        get() = throw UnsupportedOperationException()

    override val player: Player?
        @Invoke(passCallInfo = true, targetClass = Companion::class, method = "resolvePlayer")
        get() = throw UnsupportedOperationException()


    companion object {

        fun resolvePlayer(callInfo: CallInfo) : Player? {
            val user = callInfo.adapterClassInfo.instance as User

            Sandstone.server.worlds.forEach {
                it.entities.forEach {
                    if(it.uniqueId == user.uniqueId) {
                        return it as Player
                    }
                }
            }

            return null
        }
    }
}