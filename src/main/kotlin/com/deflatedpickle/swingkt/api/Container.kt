/* Copyright (c) 2021 DeflatedPickle under the MIT license */

package com.deflatedpickle.swingkt.api

import com.deflatedpickle.swingkt.impl.Constraint
import com.deflatedpickle.swingkt.impl.Layout
import java.awt.LayoutManager

abstract class Container<T : Layout<LayoutManager>, C : Constraint> : Component<C>
