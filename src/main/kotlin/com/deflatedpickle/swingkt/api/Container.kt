/* Copyright (c) 2021 DeflatedPickle under the MIT license */

package com.deflatedpickle.swingkt.api

import java.awt.LayoutManager

abstract class Container<T : Layout<LayoutManager>, C : Constraint> : Component<C> {
    internal abstract fun add(component: Component<C>)
}
