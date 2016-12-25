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

import com.github.projectsandstone.api.block.BlockType
import com.github.projectsandstone.api.entity.EntityType
import com.github.projectsandstone.api.event.command.CommandSendEvent
import com.github.projectsandstone.api.event.player.PlayerBlockInteractEvent
import com.github.projectsandstone.api.event.player.PlayerMessageChannelEvent
import com.github.projectsandstone.api.text.Text
import com.github.projectsandstone.api.world.Location
import com.github.projectsandstone.bukkit.adapter.BukkitServerAdapter
import com.github.projectsandstone.bukkit.adapter.block.BlockAdapter
import com.github.projectsandstone.bukkit.adapter.entity.living.player.PlayerAdapter
import com.github.projectsandstone.bukkit.adapter.world.ChunkFactory
import com.github.projectsandstone.bukkit.adapter.world.WorldAdapter
import com.github.projectsandstone.bukkit.converter.*
import com.github.projectsandstone.bukkit.converter.event.PlayerChatConverter
import com.github.projectsandstone.bukkit.converter.event.PlayerCommandConverter
import com.github.projectsandstone.bukkit.converter.event.PlayerInteractConverter
import com.github.projectsandstone.bukkit.converter.event.ServerCommandConverter
import com.github.projectsandstone.bukkit.util.alias.*
import com.github.projectsandstone.bukkit.util.register
import com.github.projectsandstone.common.adapter.Adapters
import org.bukkit.event.player.AsyncPlayerChatEvent
import org.bukkit.event.player.PlayerInteractEvent

object AdapterRegistry {

    fun registerAdapters() {
        // ----> Initialized Constants class too late
        val adapters = Adapters.adapters

        adapters.registerConverter(Array<Any>::class.java, List::class.java, ArrayToListConverter)
        adapters.registerConverter(BlockType::class.java, BukkitMaterial::class.java, BlockTypeConverter)
        adapters.registerConverter(Class::class.java, Class::class.java, ClassConverter)
        adapters.registerConverter(Collection::class.java, Collection::class.java, CollectionConverter)
        @Suppress("UNCHECKED_CAST")
        adapters.registerConverter(EntityType::class.java, Class::class.java as Class<Class<BukkitEntity>>, EntityTypeConverter)
        adapters.registerConverter(Location::class.java, BukkitLocation::class.java, LocationConverter)
        adapters.registerConverter(Text::class.java, String::class.java, TextConverter)
        adapters.registerConverter(BukkitPlayerCommandPreprocessEvent::class.java, CommandSendEvent::class.java, PlayerCommandConverter)
        adapters.registerConverter(BukkitServerCommandEvent::class.java, CommandSendEvent::class.java, ServerCommandConverter)
        adapters.registerConverter(PlayerInteractEvent::class.java, PlayerBlockInteractEvent::class.java, PlayerInteractConverter)
        adapters.registerConverter(AsyncPlayerChatEvent::class.java, PlayerMessageChannelEvent::class.java, PlayerChatConverter)

        // Adapters

        adapters.register(::BukkitServerAdapter)
        adapters.register(::WorldAdapter)
        adapters.register(::PlayerAdapter)
        adapters.register(::BlockAdapter)
        adapters.register(ChunkFactory)
    }

}