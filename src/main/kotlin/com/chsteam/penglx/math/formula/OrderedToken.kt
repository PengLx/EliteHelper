package com.chsteam.penglx.math.formula

/**
 * RPGCore © 2022
 */
interface OrderedToken : Token {
    val precedence: Int
}