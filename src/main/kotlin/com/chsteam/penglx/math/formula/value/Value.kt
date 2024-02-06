package com.chsteam.penglx.math.formula.value

import com.chsteam.penglx.math.formula.Token
import java.util.*

/**
 * RPGCore © 2022
 */
interface Value : Token {
    fun getValue(values: DoubleArray): Double
    override fun apply(stack: Stack<Double>, values: DoubleArray) {
        stack.push(getValue(values))
    }
}