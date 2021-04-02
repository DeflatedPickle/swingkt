/* Copyright (c) 2021 DeflatedPickle under the MIT license */

@file:Suppress("MemberVisibilityCanBePrivate", "unused")

package com.deflatedpickle.swingkt.widget.swing

import java.awt.Component as AWTComponent
import com.deflatedpickle.swingkt.api.Builder
import com.deflatedpickle.swingkt.api.Component
import com.deflatedpickle.swingkt.ComponentMap
import com.deflatedpickle.swingkt.api.Event
import com.deflatedpickle.swingkt.api.Listener
import com.deflatedpickle.swingkt.impl.Model
import com.deflatedpickle.swingkt.impl.Constraint
import com.deflatedpickle.swingkt.api.SwingDSL
import javax.swing.JSpinner

fun <C : Constraint> ComponentMap.spinner(
    constraint: C,
    block: SpinnerBuilder<C>.() -> Unit = {}
) {
    put(SpinnerBuilder(constraint).apply(block).build(), constraint)
}

inline fun <reified T : Any, C : Constraint> Spinner(
    constraint: C,
    model: Model.Spinner<*, T>,
    onChangeListeners: List<Listener> = listOf(),
) = Spinner(T::class.java, constraint, model, onChangeListeners)

@SwingDSL
data class Spinner<T : Any, C : Constraint>(
    val clazz: Class<T>,
    val constraint: C,
    val model: Model.Spinner<*, T>,
    val onChangeListeners: List<Listener> = listOf(),
) : Component<C> {
    @OptIn(ExperimentalStdlibApi::class)
    internal val widget = JSpinner().apply {
        this.model = this@Spinner.model.toAWT()

        onChangeListeners.forEach { e -> addChangeListener { e.action(Event(it.source)) } }
    }

    override fun toAWT(): AWTComponent = widget
}

@SwingDSL
class SpinnerBuilder<C : Constraint>(
    var constraint: C
) : Builder<C> {
    var model: Model.Spinner<*, Any> = Model.Spinner.Number<Int> {
        value = 0
        min = 0
        max = 0
        step = 0
    }

    val onChange = mutableListOf<Listener>()

    override fun build() = Spinner(constraint, model, onChange)
}
