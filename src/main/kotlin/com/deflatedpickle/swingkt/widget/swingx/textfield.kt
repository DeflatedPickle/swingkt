/* Copyright (c) 2021 DeflatedPickle under the MIT license */

@file:Suppress("MemberVisibilityCanBePrivate", "unused", "SpellCheckingInspection")

package com.deflatedpickle.swingkt.widget.swingx

import com.deflatedpickle.swingkt.ComponentMap
import com.deflatedpickle.swingkt.api.*
import com.deflatedpickle.swingkt.impl.Constraint
import org.jdesktop.swingx.JXTextField
import java.awt.Font
import javax.swing.Action
import java.awt.Component as AWTComponent

fun <C : Constraint> ComponentMap.textfield(
    constraint: C,
    block: TextFieldBuilder<C>.() -> Unit
) = TextFieldBuilder(constraint).apply(block).build().apply { put (this, constraint) }

@SwingDSL
data class TextField<C : Constraint>(
    val constraint: C,
    val text: String? = null,
    val prompt: String? = null,
    val columns: Int = 0,
    val alignment: Alignment = Alignment.LEFT,
    val font: Font? = null,
    val offset: Int = 0,
    val action: Action? = null,
    val onClickListeners: List<Listener> = listOf(),
) : Component<C> {
    internal val widget = JXTextField().apply {
        text = this@TextField.text
        prompt = this@TextField.prompt
        columns = this@TextField.columns
        horizontalAlignment = this@TextField.alignment.ordinal
        this@TextField.font?.let { font = it }
        this@TextField.action?.let { action = it }
        scrollOffset = this@TextField.offset

        onClickListeners.forEach { e -> addActionListener { e.action(Event(it.source)) } }
    }

    override fun toAWT(): AWTComponent = widget
}

@SwingDSL
class TextFieldBuilder<C : Constraint>(
    var constraint: C
) : Builder<C> {
    var text: String? = null
    var prompt: String? = null
    var columns = 0
    var alignment = Alignment.LEFT
    var font: Font? = null
    var offset = 0
    var action: Action? = null

    val onClick = mutableListOf<Listener>()

    override fun build() = TextField(constraint, text, prompt, columns, alignment, font, offset, action)
}
