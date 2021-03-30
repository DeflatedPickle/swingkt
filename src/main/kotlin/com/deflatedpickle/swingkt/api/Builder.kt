/* Copyright (c) 2021 DeflatedPickle under the MIT license */

package com.deflatedpickle.swingkt.api

interface Builder<C : Constraint> {
    fun build(): Component<C>
}
