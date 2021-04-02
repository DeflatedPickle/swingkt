/* Copyright (c) 2021 DeflatedPickle under the MIT license */

package com.deflatedpickle.swingkt.api

interface AwtObject <out T> {
    fun toAWT(): T
}