/* Copyright (c) 2021-2022 DeflatedPickle under the MIT license */

@file:Suppress("MemberVisibilityCanBePrivate", "unused")

package com.deflatedpickle.swingkt.widget.swing

import com.deflatedpickle.swingkt.api.Component
import com.deflatedpickle.swingkt.api.Event
import com.deflatedpickle.swingkt.api.Listener
import com.deflatedpickle.swingkt.api.Orientation
import com.deflatedpickle.swingkt.api.SwingDSL
import com.deflatedpickle.swingkt.impl.Constraint
import com.deflatedpickle.swingkt.impl.ContainerBuilder
import com.deflatedpickle.swingkt.impl.Model
import com.deflatedpickle.swingkt.impl.WidgetBuilder
import javax.swing.JSlider

fun <C : Constraint> ContainerBuilder<*, C>.slider(
    constraint: C,
    block: SliderBuilder<C>.() -> Unit = {}
) = SliderBuilder(constraint).apply(block).build().apply { components[this] = constraint }

inline fun <reified T : Any, C : Constraint> Slider(
    constraint: C,
    model: Model.BoundedRange<*, T>,
    paint: Slider.Paint,
    inverted: Boolean,
    onChangeListeners: List<Listener> = listOf(),
) = Slider(T::class.java, constraint, model, paint, inverted, onChangeListeners)

@SwingDSL
data class Slider<T : Any, C : Constraint>(
    val clazz: Class<T>,
    val constraint: C,
    val model: Model.BoundedRange<*, T>,
    val paint: Paint,
    val inverted: Boolean,
    val onChangeListeners: List<Listener> = listOf(),
) : Component<C> {
    data class Paint(
        var ticks: Boolean = false,
        var track: Boolean = true,
        var labels: Boolean = false,
    )

    data class Snap(
        var ticks: Boolean = false,
        // var values: Boolean = true,
    )

    internal val widget = JSlider().apply {
        this.model = this@Slider.model.toAWT()
        this.paintTicks = this@Slider.paint.ticks
        this.paintLabels = this@Slider.paint.labels
        this.paintTrack = this@Slider.paint.track
        this.inverted = this@Slider.inverted

        onChangeListeners.forEach { e -> addChangeListener { e.action(Event(it.source)) } }
    }

    override fun toAWT(): JSlider = widget
}

@SwingDSL
class SliderBuilder<C : Constraint>(
    var constraint: C
) : WidgetBuilder<Slider<*, *>, C>() {
    var model: Model.BoundedRange<*, Any> = Model.BoundedRange.Integer {
        value = 0
        extent = 0
        min = 0
        max = 0
    }

    var orientation = Orientation.HORIZONTAL

    internal val paint = Slider.Paint()
    fun paint(args: Slider.Paint.() -> Unit) = paint.apply(args)

    var inverted = false

    internal val snap = Slider.Snap()
    fun snap(args: Slider.Snap.() -> Unit) = snap.apply(args)

    val onChange = mutableListOf<Listener>()

    override fun build() = Slider(constraint, model, paint, inverted, onChange)
}
