/* Copyright (c) 2021 DeflatedPickle under the MIT license */

@file:Suppress("MemberVisibilityCanBePrivate", "unused")

package com.deflatedpickle.swingkt

import java.awt.Component as AWTComponent
import com.deflatedpickle.swingkt.api.Builder
import com.deflatedpickle.swingkt.api.Component
import com.deflatedpickle.swingkt.api.ComponentMap
import com.deflatedpickle.swingkt.api.Constraint
import com.deflatedpickle.swingkt.api.Container
import com.deflatedpickle.swingkt.api.Layout
import com.deflatedpickle.swingkt.api.SwingDSL
import java.awt.LayoutManager
import org.jdesktop.swingx.JXPanel

fun <C : Constraint, T : Layout<LayoutManager>> ComponentMap.panel(
    layout: T,
    constraint: C,
    block: PanelBuilder<C, T>.() -> Unit
) {
    put(PanelBuilder(constraint, layout).apply(block).build(), constraint)
}

data class Panel<C : Constraint, T : Layout<LayoutManager>>(
    val constraint: C,
    val layout: T,
    val componentList: Map<Component<C>, C>
) : Container<T, C>() {
    val widget = JXPanel().apply {
        layout = this@Panel.layout.toAWT()

        componentList.forEach { this.add(it.key.toAWT(), it.value.toAWT()) }
    }

    override fun add(component: Component<C>) {
        this.widget.add(component.toAWT())
    }

    override fun toAWT(): AWTComponent = widget
}

@SwingDSL
class PanelBuilder<C : Constraint, T : Layout<LayoutManager>>(
    var constraint: C,
    var layout: T,
) : Builder<Constraint> {
    private val components = mutableMapOf<Component<Constraint>, Constraint>()
    fun components(block: ComponentMap.() -> Unit) {
        components.putAll(ComponentMap().apply(block))
    }

    override fun build() = Panel(constraint, layout, components)
}
