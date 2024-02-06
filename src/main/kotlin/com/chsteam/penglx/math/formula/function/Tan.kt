package com.chsteam.penglx.math.formula.function

import com.chsteam.penglx.math.toRadians
import java.util.*

/**
 * RPGCore © 2022
 */
object Tan : Func {
    override val token = "tan"
    override fun apply(stack: Stack<Double>, values: DoubleArray) {
        stack.push(Math.tan(stack.pop().toRadians()))
    }
}