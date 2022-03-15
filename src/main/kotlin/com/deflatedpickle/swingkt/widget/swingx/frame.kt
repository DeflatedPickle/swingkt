/* Copyright (c) 2021 DeflatedPickle under the MIT license */

@file:Suppress("MemberVisibilityCanBePrivate", "unused")

package com.deflatedpickle.swingkt.widget.swingx

import com.deflatedpickle.swingkt.ComponentMap
import com.deflatedpickle.swingkt.api.CloseOperation
import com.deflatedpickle.swingkt.api.Container
import com.deflatedpickle.swingkt.api.SwingDSL
import com.deflatedpickle.swingkt.impl.Constraint
import com.deflatedpickle.swingkt.impl.ContainerBuilder
import com.deflatedpickle.swingkt.impl.Layout
import com.deflatedpickle.swingkt.widget.swing.menu.MenuBar
import org.jdesktop.swingx.JXFrame
import java.awt.Dimension
import java.awt.LayoutManager

fun <T : Layout<LayoutManager>> frame(
    layout: T,
    block: FrameBuilder<T>.() -> Unit = {}
) = FrameBuilder(layout).apply(block).make()

data class Frame<T : Layout<LayoutManager>>(
    val layout: T,
    val title: String = "",
    val width: Int = 420,
    val height: Int = 360,
    val closeOperation: CloseOperation = CloseOperation.EXIT,
    val menu: Pair<MenuBar<*>, Constraint>? = null,
    val componentList: ComponentMap
) : Container<T, Constraint>() {
    internal val widget: JXFrame = JXFrame().apply {
        title = this@Frame.title
        size = Dimension(
            this@Frame.width,
            this@Frame.height
        )
        defaultCloseOperation = closeOperation.ordinal
        layout = this@Frame.layout.toAWT()

        menu?.let { add(it.first.toAWT(), it.second.toAWT()) }
    }

    fun show(): Frame<T> = this.apply { widget.isVisible = true }
    fun hide(): Frame<T> = this.apply { widget.isVisible = false }

    override fun toAWT(): JXFrame = widget
}

@SwingDSL
class FrameBuilder<T : Layout<LayoutManager>>(
    var layout: T
) : ContainerBuilder<Frame<*>, Constraint>() {
    var title: String = ""
    var width: Int = 0
    var height: Int = 0
    var closeOperation: CloseOperation = CloseOperation.EXIT

    internal var menu: Pair<MenuBar<*>, Constraint>? = null

    override fun build() = Frame(layout, title, width, height, closeOperation, menu, components)
}
