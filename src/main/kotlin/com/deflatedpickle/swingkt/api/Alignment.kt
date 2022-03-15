@file:Suppress("unused")

package com.deflatedpickle.swingkt.api

import java.awt.FlowLayout

enum class Alignment {
    LEFT {
        override fun toFlow() = FlowLayout.LEFT
    },
    CENTER {
        override fun toFlow() = FlowLayout.CENTER
    },
    RIGHT {
        override fun toFlow() = FlowLayout.RIGHT
    },
    ;

    abstract fun toFlow(): Int
}