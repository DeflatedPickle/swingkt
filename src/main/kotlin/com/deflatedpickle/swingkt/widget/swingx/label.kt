/* Copyright (c) 2021 DeflatedPickle under the MIT license */

@file:Suppress("MemberVisibilityCanBePrivate", "unused")

package com.deflatedpickle.swingkt.widget.swingx

import com.deflatedpickle.swingkt.api.Component
import com.deflatedpickle.swingkt.api.SwingDSL
import com.deflatedpickle.swingkt.impl.Constraint
import com.deflatedpickle.swingkt.impl.WidgetBuilder
import org.jdesktop.swingx.JXLabel
import javax.swing.Icon

fun <C : Constraint> WidgetBuilder<*, C>.label(
    constraint: C,
    block: LabelBuilder<C>.() -> Unit = {}
) = LabelBuilder(constraint).apply(block).make().apply { components[this] = constraint }

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

    override fun toAWT(): JXLabel = widget
}

@SwingDSL
class LabelBuilder<C : Constraint>(
    var constraint: C
) : WidgetBuilder<Label<*>, C>() {
    var text: String? = null
    var icon: Icon? = null

    override fun build() = Label(constraint, text, icon)
}
