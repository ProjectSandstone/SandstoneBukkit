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
package com.github.projectsandstone.bukkit.util

import com.github.jonathanxd.adapterhelper.Adapter
import com.github.jonathanxd.adapterhelper.AdapterManager
import com.github.jonathanxd.adapterhelper.AdapterSpecification

inline fun <reified I: Any, reified R: Any> AdapterManager.adapt(instance: I): R {
    return this.adaptUnchecked(I::class.java, instance, R::class.java)
}

inline fun <I: Any, reified R: Any> AdapterManager.adapt(adaptee: Class<I>, instance: I): R {
    return this.adaptUnchecked(adaptee, instance, R::class.java)
}

inline fun <reified I: Any, reified R: Any> AdapterManager.adaptAll(instancesIterable: Iterable<I>): List<R> {
    return this.adaptAll(I::class.java, instancesIterable, R::class.java)
}

inline fun <I: Any, reified R: Any> AdapterManager.adaptAll(adaptee: Class<I>, instancesIterable: Iterable<I>): List<R> {
    return this.adaptAll(adaptee, instancesIterable, R::class.java)
}

inline fun <reified A: Any, reified O: Any> AdapterManager.register(noinline factory: (O, AdapterManager) -> A) {
    this.register(AdapterSpecification.create(factory, A::class.java, O::class.java))
}

inline fun <reified I: Any, reified O: Any> AdapterManager.convert(instance: I, adapter: Adapter<*>?): O =
    this.convert(I::class.java, O::class.java, instance, adapter).get()

