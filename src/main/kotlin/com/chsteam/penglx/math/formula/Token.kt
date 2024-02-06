package com.chsteam.penglx.math.formula

import java.util.*

/**
 * RPGCore © 2022
 */
interface Token {
    fun apply(stack: Stack<Double>, values: DoubleArray)
}