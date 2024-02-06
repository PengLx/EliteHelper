package com.chsteam.penglx.math.formula.value

/**
 * RPGCore © 2022
 */
data class ConstValue(private val value: Double) : Value {
    override fun getValue(values: DoubleArray): Double {
        return value
    }
}