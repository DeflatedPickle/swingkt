/* Copyright (c) 2021 DeflatedPickle under the MIT license */

@file:Suppress("MemberVisibilityCanBePrivate", "unused")

package com.deflatedpickle.swingkt

import com.deflatedpickle.swingkt.api.Builder
import com.deflatedpickle.swingkt.api.CloseOperation
import com.deflatedpickle.swingkt.api.Component
import com.deflatedpickle.swingkt.api.ComponentList
import com.deflatedpickle.swingkt.api.Container
import com.deflatedpickle.swingkt.api.SwingDSL
import java.awt.BorderLayout
import java.awt.Component as AWTComponent
import java.awt.Dimension
import java.awt.LayoutManager
import javax.swing.JFrame

fun frame(block: FrameBuilder.() -> Unit) = FrameBuilder().apply(block).build()

data class Frame(
    val title: String = "",
    val width: Int = 420,
    val height: Int = 360,
    val closeOperation: CloseOperation = CloseOperation.EXIT,
    val layout: LayoutManager = BorderLayout(),
    val componentList: List<Component>
) : Container() {
    internal val frame = JFrame().apply {
        title = this@Frame.title
        size = Dimension(
            this@Frame.width,
            this@Frame.height
        )
        defaultCloseOperation = closeOperation.ordinal
        layout = this@Frame.layout
        componentList.forEach { add(it.actual()) }
    }

    fun show(): Frame {
        this.frame.isVisible = true
        return this
    }

    fun hide(): Frame {
        this.frame.isVisible = false
        return this
    }

    override fun actual(): AWTComponent = frame

    override fun add(component: Component) {
        this.frame.add(component.actual())
    }
}

@SwingDSL
class FrameBuilder : Builder {
    var title: String = ""
    var width: Int = 0
    var height: Int = 0
    var closeOperation: CloseOperation = CloseOperation.EXIT
    var layout: LayoutManager = BorderLayout()

    private val components = mutableListOf<Component>()
    fun components(block: ComponentList.() -> Unit) {
        components.addAll(ComponentList().apply(block))
    }

    override fun build() = Frame(title, width, height, closeOperation, layout, components)
}
