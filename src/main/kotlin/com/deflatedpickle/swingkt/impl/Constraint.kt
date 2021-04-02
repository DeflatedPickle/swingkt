/* Copyright (c) 2021 DeflatedPickle under the MIT license */

@file:Suppress("CanSealedSubClassBeObject", "MemberVisibilityCanBePrivate", "unused")

package com.deflatedpickle.swingkt.impl

import com.deflatedpickle.swingkt.api.AwtObject
import com.deflatedpickle.swingkt.api.Compass
import java.awt.BorderLayout

sealed class Constraint : AwtObject<Any> {
    enum class Alignment {
        LEFT,
        RIGHT,
        CENTER,
    }

    data class Border(
        val side: Compass = Compass.ROSE
    ) : Constraint() {
        override fun toAWT(): String = when (side) {
            Compass.NORTH -> BorderLayout.NORTH
            Compass.EAST -> BorderLayout.EAST
            Compass.SOUTH -> BorderLayout.SOUTH
            Compass.WEST -> BorderLayout.WEST
            Compass.ROSE -> BorderLayout.CENTER
        }
    }

    data class Flow(
        val alignment: Alignment = Alignment.CENTER
    ) : Constraint() {
        override fun toAWT(): Int = this.alignment.ordinal
    }
}
