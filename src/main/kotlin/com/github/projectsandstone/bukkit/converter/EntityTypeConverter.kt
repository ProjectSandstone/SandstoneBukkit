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

import com.github.jonathanxd.adapterhelper.Adapter
import com.github.jonathanxd.adapterhelper.AdapterManager
import com.github.jonathanxd.adapterhelper.Converter
import com.github.projectsandstone.api.Sandstone
import com.github.projectsandstone.api.entity.EntityType
import com.github.projectsandstone.bukkit.util.alias.BukkitEntity

object EntityTypeConverter : Converter<EntityType, Class<BukkitEntity>> {

    @Suppress("UNCHECKED_CAST")
    override fun convert(input: EntityType, adapter: Adapter<*>?, manager: AdapterManager): Class<BukkitEntity> {
        return ClassConverter.getBukkitEquivalent(input.entityClass) as Class<BukkitEntity>
    }

    override fun revert(): Converter<Class<BukkitEntity>, EntityType> {
        return Revert
    }

    private object Revert : Converter<Class<BukkitEntity>, EntityType> {

        override fun convert(input: Class<BukkitEntity>, adapter: Adapter<*>?, manager: AdapterManager): EntityType {
            val name = input.simpleName.toLowerCase()

            return Sandstone.game.registry.getEntry(name, EntityType::class.java)
                    ?: throw IllegalArgumentException("Cannot find entity '$name' in registry.")
        }

        override fun revert(): Converter<EntityType, Class<BukkitEntity>> {
            return EntityTypeConverter
        }

    }

}