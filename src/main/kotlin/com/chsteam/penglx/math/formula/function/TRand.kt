package com.chsteam.penglx.math.formula.function

import java.util.*

object TRand : Func {
    override val token = "trand"
    override fun apply(stack: Stack<Double>, values: DoubleArray) {
        val value = stack.pop()
        stack.push(value * (Math.random() + Math.random()) / 2)
    }
}