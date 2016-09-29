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
package com.github.projectsandstone.bukkit.adapter.world

import com.flowpowered.math.vector.Vector3d
import com.github.jonathanxd.adapter.annotations.*
import com.github.jonathanxd.adapter.info.CallInfo
import com.github.jonathanxd.adapter.utils.links.LinkUtil
import com.github.projectsandstone.api.entity.Entity
import com.github.projectsandstone.api.entity.living.player.Player
import com.github.projectsandstone.api.text.Text
import com.github.projectsandstone.api.world.Chunk
import com.github.projectsandstone.api.world.Location
import com.github.projectsandstone.api.world.Selection
import com.github.projectsandstone.api.world.World
import com.github.projectsandstone.api.world.extent.Extent
import com.github.projectsandstone.bukkit.converter.CollectionConverter
import java.io.File
import java.nio.file.Path
import java.util.*

@AdapterEnv
@Adapter(target = org.bukkit.World::class, interfaces = arrayOf(World::class))
class WorldAdapter : World {

    override val uniqueId: UUID
        @Invoke(method = "getUID", returnType = Return(type = UUID::class))
        get() = throw UnsupportedOperationException()

    override val entities: Collection<Entity>
        @Invoke(method = "getEntities", returnType = Return(type = List::class, converter = Converter(registryId = CollectionConverter.id_)))
        get() = throw UnsupportedOperationException()

    override val loadedChunks: List<Chunk>
        get() = throw UnsupportedOperationException()

    override val directory: Path
        @Invoke(method = "getWorldFolder", returnType = Return(type = File::class))
        get() = throw UnsupportedOperationException()

    override val name: String
        @Invoke(method = "getName")
        get() = throw UnsupportedOperationException("not implemented")

    @Invoke(passCallInfo = true, targetClass = WorldAdapter::class, method = "sendMessageToWorld", parameters = arrayOf(Parameter(type = Text::class)))
    override fun sendMessage(text: Text) {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getLocation(position: Vector3d): Location<Extent> {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun select(from: Vector3d, to: Vector3d): Selection {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {
        // Extension method because Bukkit World doesn't have a sendMessage() method.
        @Suppress("unused")
        @JvmStatic
        fun sendMessageToWorld(callInfo: CallInfo, text: Text) {
            val bukkitWorld = callInfo.adapterClassInfo.wrappedInstance as org.bukkit.World

            val fields = callInfo.adapterClassInfo.fields
            val adapterEnvironment = LinkUtil.getAdapterEnvironment(fields).orElseThrow({ UnsupportedOperationException("Cannot find adapter field.") })

            bukkitWorld.players.forEach { player ->
                adapterEnvironment.adapt<Player>(org.bukkit.entity.Player::class.java, player).get().sendMessage(text)
            }

        }
    }
}