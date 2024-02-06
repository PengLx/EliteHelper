package com.chsteam.penglx.math.formula.value

/**
 * RPGCore © 2022
 */
data class VarValue(private val index: Int) : Value {
    override fun getValue(values: DoubleArray): Double {
        return values[index]
    }
}