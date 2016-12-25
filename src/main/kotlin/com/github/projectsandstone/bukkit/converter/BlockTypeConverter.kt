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
import com.github.projectsandstone.api.block.BlockType
import com.github.projectsandstone.common.util.extension.formatToSandstoneRegistryId
import org.bukkit.Material

object BlockTypeConverter : Converter<BlockType, Material> {

    override fun convert(input: BlockType, adapter: Adapter<*>?, manager: AdapterManager): Material {
        return try {
            Material.valueOf(input.name.toUpperCase())
        } catch (e: Exception) {
            throw IllegalArgumentException("Can't convert '${input.name}' (from BlockType: $input) to Material.", e)
        }
    }

    override fun revert(): Converter<Material, BlockType> = Revert

    private object Revert : Converter<Material, BlockType> {
        override fun convert(input: Material, adapter: Adapter<*>?, manager: AdapterManager): BlockType {

            val id = input.formatToSandstoneRegistryId()

            return Sandstone.game.registry.getEntry(id, BlockType::class.java)
                    ?: throw IllegalArgumentException("Cannot find BlockType '$id' ( Material: $input) in registry.")
        }

        override fun revert(): Converter<BlockType, Material> = BlockTypeConverter

    }
}