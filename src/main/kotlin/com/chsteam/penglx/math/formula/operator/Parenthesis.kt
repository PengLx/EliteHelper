package com.chsteam.penglx.math.formula.operator

import java.util.*

/**
 * RPGCore © 2022
 */
object Parenthesis : Operator {
    override val token = '('
    override val precedence = 0
    override fun apply(stack: Stack<Double>, values: DoubleArray) {
        throw UnsupportedOperationException("Parenthesis can't be evaluated")
    }
}