package com.chsteam.penglx.math.formula.function

import com.chsteam.penglx.math.toRadians
import java.util.*

/**
 * RPGCore © 2022
 */
object Cos : Func {
    override val token = "cos"
    override fun apply(stack: Stack<Double>, values: DoubleArray) {
        stack.push(Math.cos(stack.pop().toRadians()))
    }
}