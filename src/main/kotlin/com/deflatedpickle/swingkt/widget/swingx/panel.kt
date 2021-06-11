/* Copyright (c) 2021 DeflatedPickle under the MIT license */

@file:Suppress("MemberVisibilityCanBePrivate", "unused")

package com.deflatedpickle.swingkt.widget.swingx

import com.deflatedpickle.swingkt.ComponentMap
import com.deflatedpickle.swingkt.api.Container
import com.deflatedpickle.swingkt.api.SwingDSL
import com.deflatedpickle.swingkt.impl.Constraint
import com.deflatedpickle.swingkt.impl.Layout
import com.deflatedpickle.swingkt.impl.WidgetBuilder
import org.jdesktop.swingx.JXPanel
import java.awt.LayoutManager
import javax.swing.JComponent

fun <C : Constraint, T : Layout<LayoutManager>> WidgetBuilder<*, C>.panel(
    constraint: C,
    layout: T,
    block: PanelBuilder<C, T>.() -> Unit = {}
) = PanelBuilder(constraint, layout).apply(block).make().apply { components[this] = constraint }

data class Panel<C : Constraint, T : Layout<LayoutManager>>(
    val constraint: C,
    val layout: T,
    val componentList: ComponentMap
) : Container<T, C>() {
    val widget = JXPanel().apply {
        layout = this@Panel.layout.toAWT()
    }

    override fun toAWT(): JComponent = widget
}

@SwingDSL
class PanelBuilder<C : Constraint, T : Layout<LayoutManager>>(
    var constraint: C,
    var layout: T,
) : WidgetBuilder<Panel<*, *>, C>() {
    override fun build() = Panel(constraint, layout, components)
}
