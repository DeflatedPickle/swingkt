/* Copyright (c) 2021 DeflatedPickle under the MIT license */

@file:Suppress("MemberVisibilityCanBePrivate", "unused")

package com.deflatedpickle.swingkt

import com.deflatedpickle.swingkt.api.Builder
import com.deflatedpickle.swingkt.api.Component
import com.deflatedpickle.swingkt.api.ComponentList
import com.deflatedpickle.swingkt.api.Event
import com.deflatedpickle.swingkt.api.Listener
import com.deflatedpickle.swingkt.api.SwingDSL
import java.awt.Component as AWTComponent
import javax.swing.Action
import javax.swing.Icon
import javax.swing.JButton

data class Button(
    val text: String? = null,
    val icon: Icon? = null,
    val action: Action? = null,
    val mnemonic: Char? = null,
    val onClickListeners: List<Listener> = listOf(),
    val onChangeListeners: List<Listener> = listOf()
) : Component {
    internal val button = JButton().apply {
        text = this@Button.text
        icon = this@Button.icon
        this@Button.action?.let { action = it }
        this@Button.mnemonic?.let { mnemonic = it.toInt() }

        onClickListeners.forEach { e -> addActionListener { e.action(Event(it.source)) } }
        onChangeListeners.forEach { e -> addChangeListener { e.action(Event(it.source)) } }
    }

    override fun actual(): AWTComponent = button
}

@SwingDSL
class ButtonBuilder : Builder {
    var text: String? = null
    var icon: Icon? = null
    var action: Action? = null
    val mnemonic: Char? = null

    val onClick = mutableListOf<Listener>()
    val onChange = mutableListOf<Listener>()

    override fun build() = Button(text, icon, action, mnemonic, onClick, onChange)
}

fun ComponentList.button(block: ButtonBuilder.() -> Unit) {
    add(ButtonBuilder().apply(block).build())
}
