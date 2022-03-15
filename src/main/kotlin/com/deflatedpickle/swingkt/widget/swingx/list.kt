/* Copyright (c) 2021-2022 DeflatedPickle under the MIT license */

@file:Suppress("MemberVisibilityCanBePrivate", "unused")

package com.deflatedpickle.swingkt.widget.swingx

import com.deflatedpickle.swingkt.api.Component
import com.deflatedpickle.swingkt.api.Event
import com.deflatedpickle.swingkt.api.Layout
import com.deflatedpickle.swingkt.api.Listener
import com.deflatedpickle.swingkt.api.SelectionMode
import com.deflatedpickle.swingkt.api.Sort
import com.deflatedpickle.swingkt.api.SwingDSL
import com.deflatedpickle.swingkt.impl.Constraint
import com.deflatedpickle.swingkt.impl.ContainerBuilder
import com.deflatedpickle.swingkt.impl.Model
import com.deflatedpickle.swingkt.impl.WidgetBuilder
import org.jdesktop.swingx.JXList
import javax.swing.SortOrder
import javax.swing.event.ListDataEvent
import javax.swing.event.ListDataListener
import kotlin.collections.List as KList

fun <C : Constraint> ContainerBuilder<*, C>.list(
    constraint: C,
    block: ListBuilder<C>.() -> Unit = {}
) = ListBuilder(constraint).apply(block).build().apply { components[this] = constraint }

@SwingDSL
data class List<C : Constraint>(
    val constraint: C,
    val model: Model.List<*, *>,
    val sort: Sort,
    val selectionMode: SelectionMode,
    val layoutOrientation: Layout,
    val visibleRows: Int,
    val onSelectionListeners: KList<Listener> = listOf(),
    val onDataAddedListeners: KList<Listener> = listOf(),
    val onDataRemovedListeners: KList<Listener> = listOf(),
    val onDataChangedListeners: KList<Listener> = listOf(),
) : Component<C> {
    internal val widget = JXList().apply {
        model = this@List.model.toAWT()
        sortOrder = SortOrder.values()[sort.ordinal]
        selectionMode = this@List.selectionMode.ordinal
        layoutOrientation = this@List.layoutOrientation.ordinal
        visibleRowCount = visibleRows

        onSelectionListeners.forEach { e ->
            addListSelectionListener {
                if (!it.valueIsAdjusting) {
                    e.action(Event(it.source))
                }
            }
        }

        model.addListDataListener(object : ListDataListener {
            override fun intervalAdded(p0: ListDataEvent) =
                onDataAddedListeners.forEach { e -> e.action(Event(p0.source)) }

            override fun intervalRemoved(p0: ListDataEvent) =
                onDataRemovedListeners.forEach { e -> e.action(Event(p0.source)) }

            override fun contentsChanged(p0: ListDataEvent) =
                onDataChangedListeners.forEach { e -> e.action(Event(p0.source)) }
        })
    }

    override fun toAWT(): JXList = widget
}

@SwingDSL
class ListBuilder<C : Constraint>(
    var constraint: C
) : WidgetBuilder<List<*>, C>() {
    lateinit var model: Model.List<*, *>
    var sortOrder = Sort.DESCENDING
    var selectionMode = SelectionMode.SINGLE_SELECTION
    var layoutOrientation = Layout.VERTICAL
    var visibleRows = -1

    val onSelection = mutableListOf<Listener>()
    val onDataAdded = mutableListOf<Listener>()
    val onDataRemoved = mutableListOf<Listener>()
    val onDataChanged = mutableListOf<Listener>()

    override fun build() = List(constraint, model, sortOrder, selectionMode, layoutOrientation, visibleRows, onSelection, onDataAdded, onDataRemoved, onDataChanged)
}
