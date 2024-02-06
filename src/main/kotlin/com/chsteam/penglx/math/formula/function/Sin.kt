package com.chsteam.penglx.math.formula.function

import com.chsteam.penglx.math.toRadians
import java.util.*

/**
 * RPGCore © 2022
 */
object Sin : Func {
    override val token = "sin"
    override fun apply(stack: Stack<Double>, values: DoubleArray) {
        stack.push(Math.sin(stack.pop().toRadians()))
    }
}