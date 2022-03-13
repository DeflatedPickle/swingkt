/* Copyright (c) 2021-2022 DeflatedPickle under the MIT license */

@file:Suppress("MemberVisibilityCanBePrivate", "UNCHECKED_CAST", "unused")

package com.deflatedpickle.swingkt.impl

import com.deflatedpickle.swingkt.api.AwtObject
import com.deflatedpickle.swingkt.api.Calendar
import java.util.Date
import javax.swing.BoundedRangeModel
import javax.swing.DefaultBoundedRangeModel
import javax.swing.DefaultListModel
import javax.swing.ListModel
import javax.swing.SpinnerDateModel
import javax.swing.SpinnerListModel
import javax.swing.SpinnerModel
import javax.swing.SpinnerNumberModel
import kotlin.collections.listOf
import java.util.Date as JDate
import kotlin.Number as KNumber

sealed class Model<out M, out T> : AwtObject<M> {
    sealed class Spinner<out M : SpinnerModel, out T> : Model<SpinnerModel, T>() {
        class Date<T : JDate>(
            block: Date<T>.() -> Unit
        ) : Spinner<SpinnerDateModel, JDate>() {
            var value: T = Date() as T
            var start: Comparable<JDate> = Date()
            var end: Comparable<JDate> = Date()
            var field: Calendar = Calendar.DAY_OF_MONTH

            init {
                block(this)
            }

            override fun toAWT(): SpinnerModel = SpinnerDateModel(value, start, end, field.ordinal)
        }

        class List<T : Any?>(
            block: List<T>.() -> Unit
        ) : Spinner<SpinnerListModel, T>() {
            var values = listOf<T>()

            init {
                block(this)
            }

            override fun toAWT(): SpinnerModel = SpinnerListModel(values)
        }

        class Number<T : KNumber>(
            block: Number<T>.() -> Unit
        ) : Spinner<SpinnerNumberModel, T>() {
            var value: T = 0 as T
            var min: Comparable<T> = 0 as Comparable<T>
            var max: Comparable<T> = 0 as Comparable<T>
            var step: T = 0 as T

            init {
                block(this)
            }

            override fun toAWT() = SpinnerNumberModel(value, min, max, step)
        }
    }

    sealed class List<out M : ListModel<T>, T> : Model<M, T>() {
        class Default<T>(
            block: Default<T>.() -> Unit
        ) : List<DefaultListModel<T>, T>() {
            var values = listOf<T>()

            init {
                block(this)
            }

            override fun toAWT(): DefaultListModel<T> = DefaultListModel<T>().apply {
                addAll(values)
            }
        }
    }

    sealed class BoundedRange<out M : BoundedRangeModel, out T> : Model<BoundedRangeModel, T>() {
        class Integer(
            block: Integer.() -> Unit
        ) : BoundedRange<BoundedRangeModel, Int>() {
            var value = 0
            var extent = 0
            var min = 0
            var max = 0

            init {
                block(this)
            }

            override fun toAWT() = DefaultBoundedRangeModel(value, extent, min, max)
        }
    }
}
