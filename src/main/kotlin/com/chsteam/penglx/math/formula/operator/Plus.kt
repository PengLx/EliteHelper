package com.chsteam.penglx.math.formula.operator

import java.util.*

/**
 * RPGCore © 2022
 */
object Plus : Operator {
    override val token = '+'
    override val precedence = 1
    override fun apply(stack: Stack<Double>, values: DoubleArray) {
        stack.push(stack.pop() + stack.pop())
    }
}