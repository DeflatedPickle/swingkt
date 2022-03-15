/* Copyright (c) 2021-2022 DeflatedPickle under the MIT license */

@file:Suppress("MemberVisibilityCanBePrivate", "unused", "SpellCheckingInspection")

package com.deflatedpickle.swingkt.widget.swingx

import com.deflatedpickle.swingkt.api.Alignment
import com.deflatedpickle.swingkt.api.Component
import com.deflatedpickle.swingkt.api.Event
import com.deflatedpickle.swingkt.api.Listener
import com.deflatedpickle.swingkt.api.SwingDSL
import com.deflatedpickle.swingkt.impl.Constraint
import com.deflatedpickle.swingkt.impl.ContainerBuilder
import com.deflatedpickle.swingkt.impl.Font
import com.deflatedpickle.swingkt.impl.WidgetBuilder
import org.jdesktop.swingx.JXTextField
import javax.swing.Action
import kotlin.collections.List as KList

fun <C : Constraint> ContainerBuilder<*, C>.field(
    constraint: C,
    block: FieldBuilder<C>.() -> Unit = {}
) = FieldBuilder(constraint).apply(block).build().apply { components[this] = constraint }

@SwingDSL
data class Field<C : Constraint>(
    val constraint: C,
    val text: String? = null,
    val prompt: String? = null,
    val columns: Int = 0,
    val alignment: Alignment = Alignment.LEFT,
    val font: Font? = null,
    val offset: Int = 0,
    val action: Action? = null,
    val onClickListeners: KList<Listener> = listOf(),
) : Component<C> {
    internal val widget = JXTextField().apply {
        text = this@Field.text
        prompt = this@Field.prompt
        columns = this@Field.columns
        horizontalAlignment = this@Field.alignment.ordinal
        this@Field.font?.let { font = it.toAWT() }
        this@Field.action?.let { action = it }
        scrollOffset = this@Field.offset

        onClickListeners.forEach { e -> addActionListener { e.action(Event(it.source)) } }
    }

    override fun toAWT(): JXTextField = widget
}

@SwingDSL
class FieldBuilder<C : Constraint>(
    var constraint: C
) : WidgetBuilder<Field<*>, C>() {
    var text: String? = null
    var prompt: String? = null
    var columns = 0
    var alignment = Alignment.LEFT
    var font: Font? = null
    var offset = 0
    var action: Action? = null

    val onClick = mutableListOf<Listener>()

    override fun build() = Field(constraint, text, prompt, columns, alignment, font, offset, action)
}
