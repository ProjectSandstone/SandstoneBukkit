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

import com.flowpowered.math.vector.Vector3i
import com.github.jonathanxd.adapterhelper.Adapter
import com.github.jonathanxd.adapterhelper.AdapterManager
import com.github.projectsandstone.api.block.BlockState
import com.github.projectsandstone.api.entity.Entity
import com.github.projectsandstone.api.entity.EntityType
import com.github.projectsandstone.api.event.Source
import com.github.projectsandstone.api.text.Text
import com.github.projectsandstone.api.text.channel.MessageReceiver
import com.github.projectsandstone.api.world.Chunk
import com.github.projectsandstone.api.world.Location
import com.github.projectsandstone.api.world.World
import com.github.projectsandstone.bukkit.util.adapt
import com.github.projectsandstone.bukkit.util.adaptAll
import com.github.projectsandstone.bukkit.util.alias.BukkitEntity
import com.github.projectsandstone.bukkit.util.alias.BukkitLocation
import com.github.projectsandstone.bukkit.util.alias.BukkitWorld
import java.nio.file.Path
import java.util.*

class WorldAdapter(override val adapteeInstance: BukkitWorld, override val adapterManager: AdapterManager) : Adapter<BukkitWorld>, World {

    override val name: String = this.adapteeInstance.name

    override val directory: Path = this.adapteeInstance.worldFolder.toPath()

    override val entities: Collection<Entity>
        get() = this.adapterManager.adaptAll(this.adapteeInstance.entities)

    override val loadedChunks: List<Chunk>
        get() = this.adapterManager.adaptAll(this.adapteeInstance.loadedChunks.toList())

    override val uniqueId: UUID = this.adapteeInstance.uid

    override fun spawnEntity(type: EntityType, location: Location<*>): Entity {
        val bukkitLocation = this.adapterManager.adapt<Location<*>, BukkitLocation>(location)
        val bukkitEntityType = this.adapterManager.adapt<EntityType, Class<BukkitEntity>>(type)

        return this.adapterManager.adapt(this.adapteeInstance.spawn(bukkitLocation, bukkitEntityType))
    }

    override fun getBlock(location: Vector3i): BlockState {
        val bukkitBlock = this.adapteeInstance.getBlockAt(location.x, location.y, location.z)

        return this.adapterManager.adapt(bukkitBlock.state)
    }

    override fun setBlock(location: Vector3i, blockState: BlockState, source: Source?) {
        val bukkitBlock = this.adapteeInstance.getBlockAt(location.x, location.y, location.z)

        bukkitBlock.type = this.adapterManager.adapt(blockState.type)
    }

    override fun sendMessage(text: Text) {
        this.entities.forEach {
            if (it is MessageReceiver) {
                it.sendMessage(text)
            }
        }
    }
}