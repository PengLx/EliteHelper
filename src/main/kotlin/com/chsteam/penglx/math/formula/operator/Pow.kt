package com.chsteam.penglx.math.formula.operator

import java.util.*

/**
 * RPGCore © 2022
 */
object Pow : Operator {
    override val token = '^'
    override val precedence = 3
    override fun apply(stack: Stack<Double>, values: DoubleArray) {
        val right = stack.pop()
        stack.push(Math.pow(stack.pop(), right))
    }
}