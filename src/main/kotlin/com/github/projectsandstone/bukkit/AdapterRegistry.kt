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

import com.github.projectsandstone.api.util.toType
import com.github.projectsandstone.bukkit.adapter.BukkitServerAdapter
import com.github.projectsandstone.bukkit.adapter.entity.EntityAdapter
import com.github.projectsandstone.bukkit.adapter.entity.living.player.PlayerAdapter
import com.github.projectsandstone.bukkit.adapter.entity.living.player.UserAdapter
import com.github.projectsandstone.bukkit.adapter.world.WorldAdapter
import com.github.projectsandstone.bukkit.converter.FileToPathConverter
import com.github.projectsandstone.common.adapter.Adapters
import java.io.File
import java.nio.file.Path

object AdapterRegistry {

    fun registerAdapters() {
        // ----> Initialized Constants class too late
        val adapters = Adapters.adapters
        val factory = adapters.adapterFactory

        adapters.registerAll(this.javaClass.classLoader, "converters")

        /*
        adapters.adapterEnvironment.registerConverter(CollectionConverter.id, Collection::class.toType(), Collection::class.toType(), CollectionConverter.spec)
        */
        // ?

        adapters.adapterEnvironment.registerConverter(File::class.toType(), Path::class.toType(), FileToPathConverter.spec)

        adapters.registerAdapterSpecification(factory.fromAnnotatedClass(EntityAdapter::class.java).adapterSpecification)
        adapters.registerAdapterSpecification(factory.fromAnnotatedClass(UserAdapter::class.java).adapterSpecification)
        adapters.registerAdapterSpecification(factory.fromAnnotatedClass(PlayerAdapter::class.java).adapterSpecification)
        adapters.registerAdapterSpecification(factory.fromAnnotatedClass(BukkitServerAdapter::class.java).adapterSpecification)
        adapters.registerAdapterSpecification(factory.fromAnnotatedClass(WorldAdapter::class.java).adapterSpecification)
    }

}