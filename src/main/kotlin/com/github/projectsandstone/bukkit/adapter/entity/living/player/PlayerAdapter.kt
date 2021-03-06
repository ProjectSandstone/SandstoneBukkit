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

import com.github.jonathanxd.adapterhelper.Adapter
import com.github.jonathanxd.adapterhelper.AdapterManager
import com.github.projectsandstone.api.entity.living.player.Player
import com.github.projectsandstone.api.text.Text
import com.github.projectsandstone.bukkit.adapter.entity.EntityAdapter
import com.github.projectsandstone.bukkit.adapter.entity.living.LivingEntityAdapter
import com.github.projectsandstone.bukkit.adapter.text.channel.MessageReceiverAdapter
import com.github.projectsandstone.bukkit.util.alias.BukkitPlayer
import com.github.projectsandstone.bukkit.util.convert
import java.util.*

class PlayerAdapter(override val adapteeInstance: BukkitPlayer, override val adapterManager: AdapterManager)
    : Adapter<BukkitPlayer>, UserAdapter<BukkitPlayer>, LivingEntityAdapter<BukkitPlayer>, EntityAdapter<BukkitPlayer>, MessageReceiverAdapter<BukkitPlayer>,
        Player {

    override val uniqueId: UUID
        get() = this.adapteeInstance.uniqueId

    override fun kick() {
        this.adapteeInstance.kickPlayer("Unknown Reason!")
    }

    override fun kick(reason: Text) {
        this.adapteeInstance.kickPlayer(this.adapterManager.convert(reason, this))
    }

}