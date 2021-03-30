/* Copyright (c) 2021 DeflatedPickle under the MIT license */

package com.deflatedpickle.swingkt.api

import com.deflatedpickle.swingkt.impl.Constraint
import java.awt.Component

interface Component<out C : Constraint> : AwtObject<Component>
