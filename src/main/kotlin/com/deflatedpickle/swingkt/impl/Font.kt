/* Copyright (c) 2022 DeflatedPickle under the MIT license */

@file:Suppress("UNCHECKED_CAST")

package com.deflatedpickle.swingkt.impl

import com.deflatedpickle.swingkt.api.AwtObject
import java.awt.font.TextAttribute
import java.awt.Font as JFont

data class Font(
    val family: String = "",
    val bold: Boolean = false,
    val italic: Boolean = false,
    val underline: Boolean = false,
    val size: Int = 0,
) : AwtObject<JFont> {
    override fun toAWT(): JFont = JFont(
        family,
        (if (bold) 1 else 0) or (if (italic) 2 else 0),
        size
    ).let {
        return@let it.deriveFont(
            mutableMapOf<TextAttribute, Int>().apply {
                this[TextAttribute.UNDERLINE] = TextAttribute.UNDERLINE_ON
            }
        )
    }
}
