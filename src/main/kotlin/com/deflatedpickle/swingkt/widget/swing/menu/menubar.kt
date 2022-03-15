/* Copyright (c) 2021-2022 DeflatedPickle under the MIT license */

@file:Suppress("MemberVisibilityCanBePrivate", "unused")

package com.deflatedpickle.swingkt.widget.swing.menu

import com.deflatedpickle.swingkt.ComponentList
import com.deflatedpickle.swingkt.api.Component
import com.deflatedpickle.swingkt.api.SwingDSL
import com.deflatedpickle.swingkt.impl.Constraint
import com.deflatedpickle.swingkt.impl.MenuBuilder
import com.deflatedpickle.swingkt.widget.swingx.FrameBuilder
import javax.swing.JMenuBar

fun <C : Constraint> FrameBuilder<*>.menubar(
    constraint: C,
    block: MenuBarBuilder<C>.() -> Unit = {}
) = MenuBarBuilder(constraint).apply(block).make().also { menu = Pair(it, constraint) }

@SwingDSL
data class MenuBar<C : Constraint>(
    val constraint: C,
    val componentList: ComponentList
) : Component<C> {
    internal val widget = JMenuBar().apply {
    }

    override fun toAWT(): JMenuBar = widget
}

@SwingDSL
class MenuBarBuilder<C : Constraint>(
    var constraint: C
) : MenuBuilder<MenuBar<*>, C>() {
    override fun build() = MenuBar(constraint, components)
}
