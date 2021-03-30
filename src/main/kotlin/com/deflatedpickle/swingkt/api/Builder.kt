/* Copyright (c) 2021 DeflatedPickle under the MIT license */

package com.deflatedpickle.swingkt.api

import com.deflatedpickle.swingkt.impl.Constraint

interface Builder<C : Constraint> {
    fun build(): Component<C>
}
