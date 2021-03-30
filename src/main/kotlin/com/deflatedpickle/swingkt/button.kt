/* Copyright (c) 2021 DeflatedPickle under the MIT license */

@file:Suppress("MemberVisibilityCanBePrivate", "unused")

package com.deflatedpickle.swingkt

import java.awt.Component as AWTComponent
import com.deflatedpickle.swingkt.api.Builder
import com.deflatedpickle.swingkt.api.Component
import com.deflatedpickle.swingkt.api.ComponentMap
import com.deflatedpickle.swingkt.impl.Constraint
import com.deflatedpickle.swingkt.api.Event
import com.deflatedpickle.swingkt.api.Listener
import com.deflatedpickle.swingkt.api.SwingDSL
import javax.swing.Action
import javax.swing.Icon
import org.jdesktop.swingx.JXButton

fun <C : Constraint> ComponentMap.button(
    constraint: C,
    block: ButtonBuilder<C>.() -> Unit
) {
    put(ButtonBuilder(constraint).apply(block).build(), constraint)
}

@SwingDSL
data class Button<C : Constraint>(
    val constraint: C,
    val text: String? = null,
    val icon: Icon? = null,
    val action: Action? = null,
    val mnemonic: Char? = null,
    val onClickListeners: List<Listener> = listOf(),
    val onChangeListeners: List<Listener> = listOf()
) : Component<C> {
    internal val widget = JXButton().apply {
        text = this@Button.text
        icon = this@Button.icon
        this@Button.action?.let { action = it }
        this@Button.mnemonic?.let { mnemonic = it.toInt() }

        onClickListeners.forEach { e -> addActionListener { e.action(Event(it.source)) } }
        onChangeListeners.forEach { e -> addChangeListener { e.action(Event(it.source)) } }
    }

    override fun toAWT(): AWTComponent = widget
}

@SwingDSL
class ButtonBuilder<C : Constraint>(
    var constraint: C
) : Builder<C> {
    var text: String? = null
    var icon: Icon? = null
    var action: Action? = null
    val mnemonic: Char? = null

    val onClick = mutableListOf<Listener>()
    val onChange = mutableListOf<Listener>()

    override fun build() = Button(constraint, text, icon, action, mnemonic, onClick, onChange)
}
