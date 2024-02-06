package com.chsteam.penglx.math.formula.function

import java.util.*

/**
 * RPGCore © 2022
 */
object Sq : Func {
    override val token = "sq"
    override fun apply(stack: Stack<Double>, values: DoubleArray) {
        val value = stack.pop()
        stack.push(value * value)
    }
}