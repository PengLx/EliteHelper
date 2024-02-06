package com.chsteam.penglx.math.formula.operator

import java.util.*

/**
 * RPGCore © 2022
 */
object Mod : Operator {
    override val token = '%'
    override val precedence = 2
    override fun apply(stack: Stack<Double>, values: DoubleArray) {
        val right = stack.pop()
        if (right == 0.0) {
            stack.push(0.0)
        } else {
            stack.push(stack.pop() % right)
        }
    }
}