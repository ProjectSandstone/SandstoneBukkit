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

import com.github.projectsandstone.api.Game
import com.github.projectsandstone.api.Sandstone
import com.github.projectsandstone.api.block.BlockState
import com.github.projectsandstone.api.block.BlockType
import com.github.projectsandstone.api.entity.Entity
import com.github.projectsandstone.api.entity.EntityType
import com.github.projectsandstone.api.item.ItemType
import com.github.projectsandstone.bukkit.block.DefaultBlockState
import com.github.projectsandstone.bukkit.converter.ClassConverter
import com.github.projectsandstone.bukkit.util.alias.BukkitEntity
import com.github.projectsandstone.common.util.extension.formatToSandstoneRegistryId
import com.github.projectsandstone.common.util.extension.formatToSandstoneRegistryName
import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner
import org.bukkit.Material

internal object BukkitRegistry {

    @Suppress("UNCHECKED_CAST")
    fun register(game: Game) {
        val scan = FastClasspathScanner("org.bukkit.entity").scan()

        scan.getNamesOfSubinterfacesOf(BukkitEntity::class.java)
                .map { this.javaClass.classLoader.loadClass(it) as Class<BukkitEntity> }
                .filter { it.declaringClass == null }
                .forEach {
                    if(ClassConverter.hasSandstoneEquivalent(it)) {
                        val type = BukkitEntityType(it)
                        game.registry[type.id, EntityType::class.java] = type
                    } else {
                        Sandstone.logger.warn("No equivalent entity type for bukkit class: '$it'");
                    }
                }

        Material.values().forEach {
            val type = BukkitItemType(it, game)
            game.registry[type.id, ItemType::class.java] = type

            if (it.isBlock) {
                val blockType = BukkitBlockType(it, game)

                game.registry[blockType.id, BlockType::class.java] = blockType
            }
        }


    }

    private class BukkitEntityType(bukkitEntityClass: Class<BukkitEntity>) : EntityType {
        override val id: String = bukkitEntityClass.simpleName.formatToSandstoneRegistryId()
        override val name: String = bukkitEntityClass.simpleName.formatToSandstoneRegistryName()
        @Suppress("UNCHECKED_CAST")
        override val entityClass: Class<out Entity> = ClassConverter.getSandstoneEquivalent(bukkitEntityClass) as Class<out Entity>
    }

    private class BukkitItemType(material: Material, game: Game) : ItemType {
        override val id: String = material.formatToSandstoneRegistryId()
        override val name: String = material.formatToSandstoneRegistryName()
        override val block: BlockType? = if (material.isBlock) game.registry[id, BlockType::class.java] else null
        override val maxStack: Int = material.maxStackSize

    }

    private class BukkitBlockType(material: Material, game: Game) : BlockType {
        override val id: String = material.formatToSandstoneRegistryId()
        override val name: String = material.formatToSandstoneRegistryName()
        override val item: ItemType? = game.registry[id, ItemType::class.java]
        override val defaultState: BlockState = DefaultBlockState(this)
        override var tickRandomly: Boolean = false
    }
}
