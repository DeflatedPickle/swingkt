/* Copyright (c) 2021-2022 DeflatedPickle under the MIT license */

@file:Suppress("MemberVisibilityCanBePrivate", "unused")

package com.deflatedpickle.swingkt.widget.swing.menu

import com.deflatedpickle.swingkt.ComponentList
import com.deflatedpickle.swingkt.api.Component
import com.deflatedpickle.swingkt.api.SwingDSL
import com.deflatedpickle.swingkt.impl.Constraint
import com.deflatedpickle.swingkt.impl.MenuBuilder
import javax.swing.Action
import javax.swing.Icon
import javax.swing.JMenu

fun MenuBuilder<*, *>.menu(
    block: MenuMenuBuilder.() -> Unit = {}
) = MenuMenuBuilder().apply(block).make().apply { components.add(this) }

@SwingDSL
data class MenuMenu(
    val text: String? = null,
    val icon: Icon? = null,
    val action: Action? = null,
    val mnemonic: Char? = null,
    val componentList: ComponentList
) : Component<Constraint.None> {
    internal val widget = JMenu().apply {
        text = this@MenuMenu.text
        icon = this@MenuMenu.icon

        this@MenuMenu.action?.let { action = it }
        this@MenuMenu.mnemonic?.let { mnemonic = it.code }
    }

    override fun toAWT(): JMenu = widget
}

@SwingDSL
class MenuMenuBuilder : MenuBuilder<MenuMenu, Constraint.None>() {
    var text: String? = null
    var icon: Icon? = null
    var action: Action? = null
    val mnemonic: Char? = null

    override fun build() = MenuMenu(text, icon, action, mnemonic, components)
}
