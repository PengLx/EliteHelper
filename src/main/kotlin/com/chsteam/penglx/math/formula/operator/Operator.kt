package com.chsteam.penglx.math.formula.operator

import com.chsteam.penglx.math.formula.OrderedToken

/**
 * RPGCore © 2022
 */
interface Operator : OrderedToken {
    val token: Char
}