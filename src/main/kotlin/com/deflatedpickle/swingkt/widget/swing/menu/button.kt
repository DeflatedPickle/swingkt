/* Copyright (c) 2021-2022 DeflatedPickle under the MIT license */

@file:Suppress("MemberVisibilityCanBePrivate", "unused")

package com.deflatedpickle.swingkt.widget.swing.menu

import com.deflatedpickle.swingkt.api.Button
import com.deflatedpickle.swingkt.api.Component
import com.deflatedpickle.swingkt.api.SwingDSL
import com.deflatedpickle.swingkt.impl.Constraint
import com.deflatedpickle.swingkt.impl.MenuBuilder
import com.deflatedpickle.swingkt.impl.WidgetBuilder
import javax.swing.Action
import javax.swing.Icon
import javax.swing.JCheckBoxMenuItem
import javax.swing.JMenuItem
import javax.swing.JRadioButtonMenuItem

fun MenuBuilder<*, *>.button(
    block: MenuButtonBuilder.() -> Unit = {}
) = MenuButtonBuilder(Button.PUSH).apply(block).build().apply { components.add(this) }

fun MenuBuilder<*, *>.checkbox(
    block: MenuButtonBuilder.() -> Unit = {}
) = MenuButtonBuilder(Button.CHECK).apply(block).build().apply { components.add(this) }

fun MenuBuilder<*, *>.radiobutton(
    block: MenuButtonBuilder.() -> Unit = {}
) = MenuButtonBuilder(Button.RADIO).apply(block).build().apply { components.add(this) }

@SwingDSL
data class MenuButton(
    val type: Button,
    val text: String? = null,
    val icon: Icon? = null,
    val action: Action? = null,
    val mnemonic: Char? = null,
) : Component<Constraint.None> {
    internal val widget = when (type) {
        Button.PUSH -> JMenuItem()
        Button.CHECK -> JCheckBoxMenuItem()
        Button.RADIO -> JRadioButtonMenuItem()
    }.apply {
        text = this@MenuButton.text
        icon = this@MenuButton.icon

        this@MenuButton.action?.let { action = it }
        this@MenuButton.mnemonic?.let { mnemonic = it.code }
    }

    override fun toAWT(): JMenuItem = widget
}

@SwingDSL
class MenuButtonBuilder(
    val type: Button,
) : WidgetBuilder<MenuButton, Constraint.None>() {
    var text: String? = null
    var icon: Icon? = null
    var action: Action? = null
    val mnemonic: Char? = null

    override fun build() = MenuButton(type, text, icon, action, mnemonic)
}
