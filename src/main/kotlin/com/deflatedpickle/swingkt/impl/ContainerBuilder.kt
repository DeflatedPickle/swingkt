/* Copyright (c) 2021-2022 DeflatedPickle under the MIT license */

@file:Suppress("UNCHECKED_CAST")

package com.deflatedpickle.swingkt.impl

import com.deflatedpickle.swingkt.ComponentMap
import com.deflatedpickle.swingkt.api.Builder

abstract class ContainerBuilder<T, C : Constraint> : Builder<C> {
    internal val components = ComponentMap()

    fun make(): T {
        val widget = build()

        components.forEach {
            widget.toAWT().add(
                it.key.toAWT(),
                it.value.toAWT()
            )
        }

        return widget as T
    }
}
