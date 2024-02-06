package com.chsteam.penglx.math.formula.function

import java.util.*

/**
 * RPGCore © 2022
 */
object Floor : Func {
    override val token = "floor"
    override fun apply(stack: Stack<Double>, values: DoubleArray) {
        stack.push(Math.floor(stack.pop()))
    }
}