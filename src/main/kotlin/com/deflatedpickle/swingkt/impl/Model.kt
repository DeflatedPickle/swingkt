/* Copyright (c) 2021 DeflatedPickle under the MIT license */

@file:Suppress("MemberVisibilityCanBePrivate", "UNCHECKED_CAST", "unused")

package com.deflatedpickle.swingkt.impl

import com.deflatedpickle.swingkt.api.AwtObject
import com.deflatedpickle.swingkt.api.Calendar
import java.util.Date
import javax.swing.SpinnerDateModel
import javax.swing.SpinnerListModel
import javax.swing.SpinnerModel
import javax.swing.SpinnerNumberModel

sealed class Model<out M, out T> : AwtObject<M> {
    sealed class Spinner<out M : SpinnerModel, out T> : Model<SpinnerModel, T>() {
        class Date<T : java.util.Date>(
            block: Date<T>.() -> Unit
        ) : Spinner<SpinnerDateModel, java.util.Date>() {
            var value: T = Date() as T
            var start: Comparable<T> = Date()
            var end: Comparable<T> = Date()
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

        class Number<T : kotlin.Number>(
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
}