/* Copyright (c) 2021 DeflatedPickle under the MIT license */

@file:Suppress("MemberVisibilityCanBePrivate", "unused")

package com.deflatedpickle.swingkt

import java.awt.Component as AWTComponent
import com.deflatedpickle.swingkt.api.Builder
import com.deflatedpickle.swingkt.api.Component
import com.deflatedpickle.swingkt.api.ComponentList
import com.deflatedpickle.swingkt.api.Container
import com.deflatedpickle.swingkt.api.SwingDSL
import java.awt.BorderLayout
import java.awt.LayoutManager
import javax.swing.JPanel

data class Panel(
    val layout: LayoutManager = BorderLayout(),
    val componentList: List<Component>
) : Container() {
    internal val widget = JPanel().apply {
        layout = this@Panel.layout

        componentList.forEach { add(it.actual()) }
    }

    override fun add(component: Component) {
        this.widget.add(component.actual())
    }

    override fun actual(): AWTComponent = widget
}

@SwingDSL
class PanelBuilder : Builder {
    var layout: LayoutManager = BorderLayout()

    private val components = mutableListOf<Component>()
    fun components(block: ComponentList.() -> Unit) {
        components.addAll(ComponentList().apply(block))
    }

    override fun build() = Panel(layout, components)
}

fun ComponentList.panel(block: PanelBuilder.() -> Unit) {
    add(PanelBuilder().apply(block).build())
}
