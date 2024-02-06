package com.chsteam.penglx.math.formula.function

import java.util.*

/**
 * RPGCore © 2022
 */
object Ceil : Func {
    override val token = "ceil"
    override fun apply(stack: Stack<Double>, values: DoubleArray) {
        stack.push(Math.ceil(stack.pop()))
    }
}