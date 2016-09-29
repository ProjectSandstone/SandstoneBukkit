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
package com.github.projectsandstone.bukkit.adapter

import com.github.jonathanxd.adapter.annotations.*
import com.github.projectsandstone.api.Server
import com.github.projectsandstone.api.world.World
import com.github.projectsandstone.bukkit.converter.CollectionConverter

@AdapterEnv
@Adapter(target = org.bukkit.Server::class, interfaces = arrayOf(Server::class))
class BukkitServerAdapter : Server {

    override val ip: String
        @Invoke(method = "getIp")
        get() = throw UnsupportedOperationException()

    override val maxPlayers: Int
        @Invoke(method = "getMaxPlayers")
        get() = throw UnsupportedOperationException()

    override val motd: String
        @Invoke(method = "getMotd")
        get() = throw UnsupportedOperationException()

    override val port: Int
        @Invoke(method = "getPort")
        get() = throw UnsupportedOperationException()

    override val serverName: String
        @Invoke(method = "getServerName")
        get() = throw UnsupportedOperationException()

    override val worlds: List<World>
        @Invoke(method = "getWorlds", returnType = Return(type = List::class, converter = Converter(registryId = CollectionConverter.id_)))
        get() = throw UnsupportedOperationException()
}