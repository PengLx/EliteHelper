package com.chsteam.penglx.math.formula.function

import java.util.*

/**
 * RPGCore © 2022
 */
object Abs : Func {
    override val token = "abs"
    override fun apply(stack: Stack<Double>, values: DoubleArray) {
        stack.push(Math.abs(stack.pop()))
    }
}