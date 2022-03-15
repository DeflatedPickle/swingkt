/* Copyright (c) 2021-2022 DeflatedPickle under the MIT license */

@file:Suppress("UNCHECKED_CAST")

package com.deflatedpickle.swingkt.impl

import com.deflatedpickle.swingkt.ComponentList
import com.deflatedpickle.swingkt.api.Builder

abstract class MenuBuilder<T, C : Constraint> : Builder<C> {
    internal val components = ComponentList()

    fun make(): T {
        val widget = build()

        components.forEach {
            widget.toAWT().add(it.toAWT())
        }

        return widget as T
    }
}
