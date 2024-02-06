package com.chsteam.penglx.math.formula.operator



/**
 * RPGCore © 2022
 */
enum class Operators(val token: Operator) {
    ADDITION(Plus),
    DIVISION(Divide),
    EXPONENT(Pow),
    MODULO(Mod),
    MULTIPLICATION(Times),
    SUBTRACTION(Minus);
}