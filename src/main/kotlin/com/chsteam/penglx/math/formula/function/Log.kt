package com.chsteam.penglx.math.formula.function

import java.util.*
import kotlin.math.ln
import kotlin.math.max

/**
 * RPGCore © 2022
 */
object Log : Func {
    override val token = "log"
    override fun apply(stack: Stack<Double>, values: DoubleArray) {
        val value = stack.pop()
        if (value < 1e-10) stack.push(0.0)
        else stack.push(ln(value))
    }
}