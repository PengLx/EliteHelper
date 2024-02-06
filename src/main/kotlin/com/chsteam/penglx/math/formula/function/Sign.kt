package com.chsteam.penglx.math.formula.function

import java.util.*

/**
 * RPGCore © 2022
 */
object Sign : Func {
    override val token = "sign"
    override fun apply(stack: Stack<Double>, values: DoubleArray) {
        val value = stack.pop()
        val result = when {
            value > 0 -> 1.0
            value < 0 -> -1.0
            else -> 0.0
        }
        stack.push(result)
    }
}