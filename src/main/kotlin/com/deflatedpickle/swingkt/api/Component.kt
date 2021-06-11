/* Copyright (c) 2021 DeflatedPickle under the MIT license */

package com.deflatedpickle.swingkt.api

import com.deflatedpickle.swingkt.impl.Constraint
import java.awt.Container as AWTComponent

interface Component<out C : Constraint> : AwtObject<AWTComponent>
