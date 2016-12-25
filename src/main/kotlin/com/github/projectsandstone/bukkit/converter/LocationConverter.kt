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
import com.github.projectsandstone.api.world.Location
import com.github.projectsandstone.api.world.World
import com.github.projectsandstone.bukkit.util.alias.BukkitLocation
import com.github.projectsandstone.bukkit.util.alias.BukkitWorld
import com.github.projectsandstone.bukkit.util.toFlowVector

object LocationConverter : Converter<Location<*>, BukkitLocation> {

    @Suppress("UNCHECKED_CAST")
    override fun convert(input: Location<*>, adapter: Adapter<*>?, manager: AdapterManager): BukkitLocation {
        val worldLocation = input.getWorldLocation()
        val position = worldLocation.position

        val bukkitWorld = (worldLocation.extent as Adapter<BukkitWorld>).adapteeInstance

        return BukkitLocation(bukkitWorld, position.x, position.y, position.z)

    }

    override fun revert(): Converter<BukkitLocation, Location<*>> {
        return Revert
    }

    private object Revert : Converter<BukkitLocation, Location<*>> {

        override fun convert(input: BukkitLocation, adapter: Adapter<*>?, manager: AdapterManager): Location<*> {
            val extent = manager.adaptUnchecked(BukkitWorld::class.java, input.world, World::class.java)

            return Location(extent, input.toFlowVector())
        }

        override fun revert(): Converter<Location<*>, BukkitLocation> {
            return LocationConverter
        }

    }

}