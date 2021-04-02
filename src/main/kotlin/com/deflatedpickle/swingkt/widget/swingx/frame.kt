/* Copyright (c) 2021 DeflatedPickle under the MIT license */

@file:Suppress("MemberVisibilityCanBePrivate", "unused")

package com.deflatedpickle.swingkt.widget.swingx

import java.awt.Component as AWTComponent
import com.deflatedpickle.swingkt.api.Builder
import com.deflatedpickle.swingkt.api.CloseOperation
import com.deflatedpickle.swingkt.api.Component
import com.deflatedpickle.swingkt.ComponentMap
import com.deflatedpickle.swingkt.impl.Constraint
import com.deflatedpickle.swingkt.api.Container
import com.deflatedpickle.swingkt.impl.Layout
import com.deflatedpickle.swingkt.api.SwingDSL
import java.awt.Dimension
import java.awt.LayoutManager
import org.jdesktop.swingx.JXFrame

fun <T : Layout<LayoutManager>> frame(
    layout: T,
    block: FrameBuilder<T>.() -> Unit = {}
) = FrameBuilder(layout).apply(block).build()

data class Frame<T : Layout<LayoutManager>>(
    val layout: T,
    val title: String = "",
    val width: Int = 420,
    val height: Int = 360,
    val closeOperation: CloseOperation = CloseOperation.EXIT,
    val componentList: Map<Component<Constraint>, Constraint>
) : Container<T, Constraint>() {
    internal val widget: JXFrame = JXFrame().apply {
        title = this@Frame.title
        size = Dimension(
            this@Frame.width,
            this@Frame.height
        )
        defaultCloseOperation = closeOperation.ordinal
        layout = this@Frame.layout.toAWT()

        componentList.forEach { this.add(it.key.toAWT(), it.value.toAWT()) }
    }

    fun show(): Frame<T> = this.apply { widget.isVisible = true }
    fun hide(): Frame<T> = this.apply { widget.isVisible = false }

    override fun toAWT(): AWTComponent = widget

    override fun add(component: Component<Constraint>) {
        this.widget.add(component.toAWT())
    }
}

@SwingDSL
class FrameBuilder<T : Layout<LayoutManager>>(
    var layout: T
) : Builder<Constraint> {
    var title: String = ""
    var width: Int = 0
    var height: Int = 0
    var closeOperation: CloseOperation = CloseOperation.EXIT

    private val components = mutableMapOf<Component<Constraint>, Constraint>()
    infix fun components(block: ComponentMap.() -> Unit) {
        components.putAll(ComponentMap().apply(block))
    }

    override fun build() = Frame(layout, title, width, height, closeOperation, components)
}
