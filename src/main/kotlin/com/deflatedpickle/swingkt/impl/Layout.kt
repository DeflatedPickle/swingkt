/* Copyright (c) 2021 DeflatedPickle under the MIT license */

@file:Suppress("CanSealedSubClassBeObject", "unused", "MemberVisibilityCanBePrivate")

package com.deflatedpickle.swingkt.impl

import com.deflatedpickle.swingkt.api.Alignment
import com.deflatedpickle.swingkt.api.AwtObject
import java.awt.BorderLayout
import java.awt.FlowLayout
import java.awt.LayoutManager
import kotlin.reflect.KClass

sealed class Layout<out T : LayoutManager> : AwtObject<LayoutManager> {
    data class Border(
        val hGap: Int = 0,
        val vGap: Int = 0
    ) : Layout<BorderLayout>() {
        override fun toConstraint(): KClass<out Constraint> = Constraint.Border::class

        override fun toAWT(): BorderLayout = BorderLayout().apply {
            this.hgap = this@Border.hGap
            this.vgap = this@Border.vGap
        }
    }

    data class Flow(
        val alignment: Alignment = Alignment.CENTER,
        val alignOnBaseline: Boolean = false,
        val hGap: Int = 5,
        val vGap: Int = 5
    ) : Layout<FlowLayout>() {
        override fun toConstraint(): KClass<out Constraint> = Constraint.Flow::class

        override fun toAWT(): FlowLayout = FlowLayout().apply {
            this.alignment = this@Flow.alignment.toFlow()
            this.alignOnBaseline = this@Flow.alignOnBaseline
            this.hgap = this@Flow.hGap
            this.vgap = this@Flow.vGap
        }
    }

    abstract fun toConstraint() : KClass<out Constraint>
}
