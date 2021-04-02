/* Copyright (c) 2021 DeflatedPickle under the MIT license */

@file:Suppress("MemberVisibilityCanBePrivate", "unused")

package com.deflatedpickle.swingkt.widget.swingx

import java.awt.Component as AWTComponent
import com.deflatedpickle.swingkt.api.Builder
import com.deflatedpickle.swingkt.api.Component
import com.deflatedpickle.swingkt.ComponentMap
import com.deflatedpickle.swingkt.impl.Constraint
import com.deflatedpickle.swingkt.api.SwingDSL
import javax.swing.Icon
import org.jdesktop.swingx.JXLabel

fun <C : Constraint> ComponentMap.label(
    constraint: C,
    block: LabelBuilder<C>.() -> Unit = {}
) = LabelBuilder(constraint).apply(block).build().apply { put(this, constraint) }

@SwingDSL
data class Label<C : Constraint>(
    val constraint: C,
    val text: String? = null,
    val icon: Icon? = null,
) : Component<C> {
    internal val widget = JXLabel().apply {
        text = this@Label.text
        icon = this@Label.icon
    }

    override fun toAWT(): AWTComponent = widget
}

@SwingDSL
class LabelBuilder<C : Constraint>(
    var constraint: C
) : Builder<C> {
    var text: String? = null
    var icon: Icon? = null

    override fun build() = Label(constraint, text, icon)
}
