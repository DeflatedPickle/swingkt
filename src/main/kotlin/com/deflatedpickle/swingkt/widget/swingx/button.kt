/* Copyright (c) 2021-2022 DeflatedPickle under the MIT license */

@file:Suppress("MemberVisibilityCanBePrivate", "unused")

package com.deflatedpickle.swingkt.widget.swingx

import com.deflatedpickle.swingkt.api.Component
import com.deflatedpickle.swingkt.api.Event
import com.deflatedpickle.swingkt.api.Listener
import com.deflatedpickle.swingkt.api.SwingDSL
import com.deflatedpickle.swingkt.impl.Constraint
import com.deflatedpickle.swingkt.impl.WidgetBuilder
import org.jdesktop.swingx.JXButton
import javax.swing.Action
import javax.swing.Icon
import kotlin.collections.List as KList

fun <C : Constraint> WidgetBuilder<*, C>.button(
    constraint: C,
    block: ButtonBuilder<C>.() -> Unit = {}
) = ButtonBuilder(constraint).apply(block).make().apply { components[this] = constraint }

@SwingDSL
data class Button<C : Constraint>(
    val constraint: C,
    val text: String? = null,
    val icon: Icon? = null,
    val action: Action? = null,
    val mnemonic: Char? = null,
    val onClickListeners: KList<Listener> = listOf(),
    val onChangeListeners: KList<Listener> = listOf()
) : Component<C> {
    internal val widget = JXButton().apply {
        text = this@Button.text
        icon = this@Button.icon
        this@Button.action?.let { action = it }
        this@Button.mnemonic?.let { mnemonic = it.code }

        onClickListeners.forEach { e -> addActionListener { e.action(Event(it.source)) } }
        onChangeListeners.forEach { e -> addChangeListener { e.action(Event(it.source)) } }
    }

    override fun toAWT(): JXButton = widget
}

@SwingDSL
class ButtonBuilder<C : Constraint>(
    var constraint: C
) : WidgetBuilder<Button<*>, C>() {
    var text: String? = null
    var icon: Icon? = null
    var action: Action? = null
    val mnemonic: Char? = null

    val onClick = mutableListOf<Listener>()
    val onChange = mutableListOf<Listener>()

    override fun build() = Button(constraint, text, icon, action, mnemonic, onClick, onChange)
}
