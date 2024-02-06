package com.chsteam.penglx.math.formula.function

import com.chsteam.penglx.math.formula.OrderedToken

/**
 * RPGCore © 2022
 */
interface Func : OrderedToken {
    val token: String
    override val precedence: Int
        get() = 0
}
